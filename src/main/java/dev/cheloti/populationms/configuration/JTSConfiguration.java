package dev.cheloti.populationms.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wololo.jts2geojson.GeoJSONReader;

@Configuration
public class JTSConfiguration {
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JtsModule());
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }
    @Bean
    public GeoJSONReader geoJSONReader(){
        return new GeoJSONReader();
    }

}
