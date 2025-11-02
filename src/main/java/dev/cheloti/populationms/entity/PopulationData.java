package dev.cheloti.populationms.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PopulationData {
    private Long id;
    @NotNull(message = "County ID is mandatory")
    @Positive(message = "County ID must be a positive number")
    private Integer countyId;
    @NotNull(message = "Year is mandatory")
    @Min(value=1900, message = "Year must be at least 1900")
    @Max(value=2100, message = "Year must not exceed 2100")
    private Integer year;
    @NotNull(message = "population is mandatory")
    @PositiveOrZero (message = "population must be zero or positive")
    private Long population;

    public PopulationData(Integer countyId, Integer year, Long population) {
        this.countyId = countyId;
        this.year = year;
        this.population = population;
    }
}
