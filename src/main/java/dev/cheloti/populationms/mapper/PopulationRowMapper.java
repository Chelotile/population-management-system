package dev.cheloti.populationms.mapper;


import dev.cheloti.populationms.domain.CountyPopulation;
import dev.cheloti.populationms.utils.GeoJsonReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Polygon;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
@RequiredArgsConstructor
@Slf4j
public class PopulationRowMapper implements RowMapper<CountyPopulation> {

    private final GeoJsonReader geoJsonReader;

    @Override
    public CountyPopulation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CountyPopulation(
                rs.getString("county_name"),
                rs.getInt("county_code"),
                rs.getObject("population", Long.class),
                geoJsonReader.parseGeoJsonToPolygon(rs.getString("geometry"))

        );
    }





}
