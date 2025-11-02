package dev.cheloti.populationms.repository.impl;

import dev.cheloti.populationms.domain.CountyPopulation;
import dev.cheloti.populationms.mapper.PopulationRowMapper;
import dev.cheloti.populationms.repository.PopulationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PopulationRepositoryImpl implements PopulationRepository {

    private final JdbcClient jdbcClient;
    private final PopulationRowMapper populationRowMapper;

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
                .query(populationRowMapper)
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
                .query(populationRowMapper)
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
                .query(populationRowMapper)
                .list();
    }
}
