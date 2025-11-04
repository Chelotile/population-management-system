package dev.cheloti.populationms.repository.impl;

import dev.cheloti.populationms.domain.CountyPop;
import dev.cheloti.populationms.domain.CountyPopulation;
import dev.cheloti.populationms.domain.PopulationGrowth;
import dev.cheloti.populationms.entity.PopulationData;
import dev.cheloti.populationms.mapper.CountyPopRowMapper;
import dev.cheloti.populationms.mapper.PopulationGrowthRowMapper;
import dev.cheloti.populationms.mapper.PopulationGeomRowMapper;
import dev.cheloti.populationms.mapper.PopulationRowMapper;
import dev.cheloti.populationms.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PopulationRepositoryImpl implements PopulationRepository {

    private final JdbcClient jdbcClient;
    private final PopulationGeomRowMapper populationGeomRowMapper;
    private final PopulationGrowthRowMapper populationGrowthRowMapper;
    private final PopulationRowMapper populationRowMapper;
    private final CountyPopRowMapper countyPopRowMapper;

    @Override
    public List<CountyPopulation> getCountiesWithPopulationByYear(int year) {

        String querySql = """ 
                SELECT c.county_name,
                       c.county_code,
                       p.population,
                       ST_AsGeoJSON(c.geometry) as geometry
                FROM counties c
                LEFT JOIN population_data p on c.id = p.county_id
                AND p.year = ?
                ORDER BY c.county_code
                """;

        return jdbcClient.sql(querySql)
                .param(year)
                .query(populationGeomRowMapper)
                .list();
    }

    @Override
    public List<CountyPopulation> getCountiesAbovePopulation(int year, int minPopulation) {
        String querySql = """
                SELECT c.county_name,
                       c.county_code,
                       p.population,
                       ST_AsGeoJSON(c.geometry) as geometry
                FROM counties c
                LEFT JOIN population_data p on c.id = p.county_id
                WHERE p.year = ?
                   AND p.population > ?
                ORDER BY c.county_code
                """;
        return jdbcClient.sql(querySql)
                .param(year)
                .param(minPopulation)
                .query(populationGeomRowMapper)
                .list();
    }

    @Override
    public  List<CountyPopulation> getCountyPopHistory(String name) {
        String querySql = """
                SELECT c.county_name,
                c.county_code,
                p.year,
                p.population,
                ST_AsGeoJSON(c.geometry) as geometry
                FROM population_data p
                LEFT JOIN counties c on p.county_id = c.id
                WHERE c.county_name = ?
                ORDER BY p.year
                """;
        return jdbcClient.sql(querySql)
                .param(name)
                .query(populationGeomRowMapper)
                .list();
    }

    @Override
    public List<CountyPopulation> getCountyPopHistoryByCode(int code) {
        String querySql = """
                SELECT c.county_name,
                       c.county_code,
                       p.population,
                       ST_AsGeoJSON(ST_Simplify(c.geometry), 0.001) as geometry
                FROM counties c
                LEFT JOIN population_data p on c.id = p.county_id
                WHERE c.county_code = ?
                ORDER BY p.year
                """;
        return jdbcClient.sql(querySql)
                .param(code)
                .query(populationGeomRowMapper)
                .list();
    }

    @Override
    public List<PopulationGrowth> getPopulationGrowthRate(int yearOne, int yearTwo) {
        try {
            String querySql = """
                SELECT c.county_name,
                       c.county_code,
                       p1.year as year_one,
                       p2.year as year_two,
                       p1.population as year_one_population,
                       p2.population as year_two_population,
                ROUND(
                    ((p2.population - p1.population)::DECIMAL /
                    NULLIF(p1.population,0)) * 100, 2
                ) as growth_rate,
                ST_AsGeoJSON(ST_Simplify(c.geometry::geometry, 0.001)) as geometry
                FROM counties c
                JOIN population_data p1 on c.id = p1.county_id AND p1.year = ?
                JOIN population_data p2 on c.id = p2.county_id AND p2.year = ?
                ORDER BY c.county_code
                """;
            return jdbcClient.sql(querySql)
                    .param(yearOne)
                    .param(yearTwo)
                    .query(populationGrowthRowMapper)
                    .list();
        } catch (BadSqlGrammarException e) {
            log.error("SQL grammar error in query: {}", e.getSql());
            log.error("SQL Exception: ", e.getSQLException());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PopulationData> getPopHistory() {

        try {
            String querySQL = """
                    SELECT
                        p.id,
                        p.county_id,
                        p.year,
                        p.population
                    FROM population_data p
                    """;
            return jdbcClient.sql(querySQL)
                    .query(populationRowMapper)
                    .list();
        } catch (BadSqlGrammarException e) {
            log.error("SQL grammar error in query: {}", e.getSql());
            log.error("SQL Exception: ", e.getSQLException());
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<CountyPop> getCountiesPop() {
        try {
            String sqlQuery = """
                    SELECT
                        c.county_code,
                        c.county_name,
                    JSON_AGG(
                         JSON_BUILD_OBJECT(
                         'year', p.year,
                         'population', p.population
                         )ORDER BY p.year
                         ) as data,
                         ST_AsGeoJSON(ST_Simplify(c.geometry::geometry, 0.001)) as geometry
                    FROM counties c
                    LEFT JOIN population_data p on c.id = p.county_id
                    GROUP BY c.county_code, c.county_name, c.geometry
                    """;
            return jdbcClient.sql(sqlQuery)
                    .query(countyPopRowMapper)
                    .list();
        } catch (BadSqlGrammarException e) {
            log.error(e.getMessage());
            log.error(e.getSql());
            throw new RuntimeException(e);
        }
    }


}
