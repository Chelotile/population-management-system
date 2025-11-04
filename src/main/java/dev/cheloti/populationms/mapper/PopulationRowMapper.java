package dev.cheloti.populationms.mapper;

import dev.cheloti.populationms.entity.PopulationData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PopulationRowMapper implements RowMapper<PopulationData> {
    @Override
    public PopulationData mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PopulationData(
                rs.getLong("id"),
                rs.getInt("county_id"),
                rs.getInt("year"),
                rs.getLong("population")
        );
    }
}
