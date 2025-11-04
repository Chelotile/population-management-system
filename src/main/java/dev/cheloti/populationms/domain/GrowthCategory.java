package dev.cheloti.populationms.domain;

import dev.cheloti.populationms.enumeration.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;


@Setter
@NoArgsConstructor
@Getter
public class GrowthCategory {
    private String name;
    private Integer code;
    private Category category;
    private Double area;
    private Polygon geometry;
}
