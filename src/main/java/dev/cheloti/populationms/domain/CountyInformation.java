package dev.cheloti.populationms.domain;

import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;

public record CountyInformation(
        String name,
        Integer code,
        BigDecimal Area,
        Polygon geometry
) {
}
