package dev.cheloti.populationms.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;
import org.wololo.jts2geojson.GeoJSONReader;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeoJsonReader {

    private final GeoJSONReader reader;

    public Polygon parseGeoJsonToPolygon(String geoJson) {
        try {
            log.info("geoJson: {}", geoJson);
            if (geoJson == null || geoJson.isEmpty()) {
                throw new IllegalArgumentException("geoJson is null or empty");
            }

            return (Polygon) reader.read(geoJson);
        } catch (Exception e) {
            log.error( e.getMessage());
            throw new RuntimeException("Failed to parse geoJson",e);
        }
    }


}
