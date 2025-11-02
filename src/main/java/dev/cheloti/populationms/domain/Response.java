package dev.cheloti.populationms.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response(
        String message,
        HttpStatus status,
        Integer statusCode,
        Map<?,? > data
) {}
