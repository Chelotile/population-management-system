package dev.cheloti.populationms.mapper;

import dev.cheloti.populationms.domain.CountyInformation;
import dev.cheloti.populationms.entity.County;
import dev.cheloti.populationms.utils.GeoJsonReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CountyRowMapper implements RowMapper<CountyInformation> {
    private final GeoJsonReader reader;

    @Override
    public CountyInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CountyInformation(
                rs.getString("county_name"),
                rs.getInt("county_code"),
                rs.getBigDecimal("area_sq_km"),
                reader.parseGeoJsonToPolygon(rs.getString("geometry"))
        );
    }
}
