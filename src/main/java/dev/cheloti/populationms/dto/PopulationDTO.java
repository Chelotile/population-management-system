package dev.cheloti.populationms.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopulationDTO {
    private Integer countyId;
    private Integer year;
    private Long population;

}
