package dev.cheloti.populationms.mapper;

import dev.cheloti.populationms.domain.PopulationGrowth;
import dev.cheloti.populationms.utils.GeoJsonReader;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class PopulationGrowthRowMapper implements RowMapper<PopulationGrowth> {

    private final GeoJsonReader reader;
    @Override
    public PopulationGrowth mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PopulationGrowth(
                rs.getString("county_name"),
                rs.getInt("county_code"),
                rs.getInt("year_one"),
                rs.getInt("year_two"),
                rs.getLong("year_one_population"),
                rs.getLong("year_two_population"),
                rs.getDouble("growth_rate"),
                reader.parseGeoJsonToPolygon(rs.getString("geometry"))
        );
    }
}
