package dev.cheloti.populationms.domain;

import org.locationtech.jts.geom.Polygon;

public record PopulationGrowth(
        String name,
        Integer code,
        Integer yearOne,
        Integer yearTwo,
        Long populationYearOne,
        Long populationYearTwo,
        Double growthRate,
        Polygon geometry
) {
    public boolean isGrowing(){
        return growthRate()>0;
    }
    public boolean isDeclining(){
        return growthRate()<0;
    }
}
