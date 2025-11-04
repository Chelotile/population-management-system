package dev.cheloti.populationms.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cheloti.populationms.domain.CountyPop;
import dev.cheloti.populationms.utils.GeoJsonReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
@RequiredArgsConstructor
@Slf4j
public class CountyPopRowMapper implements RowMapper<CountyPop>{
    private final GeoJsonReader reader;

    @Override
    public CountyPop mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CountyPop(
                rs.getInt("county_code"),
                rs.getString("county_name"),
                parseJson(rs.getString("data")),
                reader.parseGeoJsonToPolygon(rs.getString("geometry"))

        );
    }

    private List<CountyPop.Data> parseJson(String json) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            log.info("Parsing json: {}", json);
            var data = mapper.readValue(json, new TypeReference<List<CountyPop.Data>>(){});

            return data;

        } catch (JsonProcessingException e) {

            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
