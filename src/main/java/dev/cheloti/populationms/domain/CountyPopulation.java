package dev.cheloti.populationms.domain;

import org.locationtech.jts.geom.Polygon;

public record CountyPopulation(
        String name,
        Integer code,
        Long population,
        Polygon geometry
) { }
