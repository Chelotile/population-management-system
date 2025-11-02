package dev.cheloti.populationms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;

@NoArgsConstructor
@Setter
@Getter
public class DataInformation {
    private String name;
    private Integer code;
    private Double popDensity;
    private Polygon geometry;
}
