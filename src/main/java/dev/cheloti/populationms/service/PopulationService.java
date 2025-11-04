package dev.cheloti.populationms.service;

import dev.cheloti.populationms.domain.*;
import dev.cheloti.populationms.dto.PopulationDTO;

import java.util.List;
import java.util.Optional;

public interface PopulationService {

    List<CountyPopulation> findCountiesWithPopulationsByYear(int year);
    List<CountyPopulation> findCountiesAbovePopulations(int year, int minPopulation);
    List<CountyPopulation> findCountyPopHistory(String name);
    List<DataInformation> findCountiesPopDensityByYear(int year);
    List<DataInformation> findCountyPopDensityByCode(int code);
    List<PopulationGrowth> findPopulationGrowthRate(int yearOne, int yearTwo);
    List<GrowthCategory> findPopulationGrowthCategory(int yearOne, int yearTwo);
    List<PopulationDTO>  findPopHistory();
    List<CountyPop> findCountyPop(String name);
    List<CountyPop> findCountiesPopHistory();
    Optional<CountyPop> findCountyPopHistoryByCode(int code);


}
