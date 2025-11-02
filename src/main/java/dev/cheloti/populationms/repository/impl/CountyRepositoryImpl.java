package dev.cheloti.populationms.repository.impl;

import dev.cheloti.populationms.domain.CountyInformation;
import dev.cheloti.populationms.mapper.CountyRowMapper;
import dev.cheloti.populationms.repository.CountyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class CountyRepositoryImpl implements CountyRepository {
    private final JdbcClient jdbcClient;
    private final CountyRowMapper countyRowMapper;

    @Override
    public Optional<CountyInformation> getCountyById(int id) {
        String querySql = """
                SELECT c.county_name,
                       c.county_code,
                       c.area_sq_km,
                       ST_AsGeoJSON(c.geometry) as geometry
                FROM counties c
                WHERE c.id = ?
                """;
        return jdbcClient.sql(querySql)
                .param(id)
                .query(countyRowMapper)
                .optional();
    }

    @Override
    public List<CountyInformation> getAllCounties() {
        return List.of();
    }

    @Override
    public CountyInformation getCountyByName(String name) {
        return null;
    }

    @Override
    public CountyInformation getCountyByCode(int code) {
        return null;
    }
}
