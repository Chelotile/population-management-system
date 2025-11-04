package dev.cheloti.populationms.dtoMapper;

import dev.cheloti.populationms.dto.PopulationDTO;
import dev.cheloti.populationms.entity.PopulationData;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PopulationDTOMapper {

    public static PopulationDTO fromPopulation(PopulationData population) {

        PopulationDTO populationDTO = new PopulationDTO();

        BeanUtils.copyProperties(population, populationDTO);

        return populationDTO;
    }


    public static PopulationData toPopulationDTO(PopulationDTO populationDTO) {
        PopulationData populationData = new PopulationData();
        BeanUtils.copyProperties(populationDTO, populationData);
        return populationData;
    }
}
