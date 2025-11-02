package dev.cheloti.populationms.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class County {
    private Long id;
    @NotBlank(message = "County name is mandatory")
    private String name;
    @NotNull(message = "County code is mandatory")
    @Positive(message = "County code must be positive")
    private Integer code;
    @DecimalMin(value="0.0", inclusive=false, message = "Area must be positive")
    @Digits(integer = 8, fraction = 4, message = "Area must have max 8 integer digits and 4 decimal digits")
    private BigDecimal area;
    @NotNull(message = "Geometry is mandatory")
    private Polygon geometry;

    public County(String name, Integer code, BigDecimal area, Polygon geometry) {
        this.name = name;
        this.code = code;
        this.area = area;
        this.geometry = geometry;
    }
}
