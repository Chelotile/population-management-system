package dev.cheloti.populationms.repository;

import dev.cheloti.populationms.domain.CountyPop;
import dev.cheloti.populationms.domain.CountyPopulation;
import dev.cheloti.populationms.domain.PopulationGrowth;
import dev.cheloti.populationms.dto.PopulationDTO;
import dev.cheloti.populationms.entity.PopulationData;

import java.util.List;
import java.util.Optional;

public interface PopulationRepository {
    List<CountyPopulation> getCountiesWithPopulationByYear(int year);
    List<CountyPopulation> getCountiesAbovePopulation(int year, int minPopulation);
    List<CountyPopulation> getCountyPopHistory(String name);
    List<CountyPopulation> getCountyPopHistoryByCode(int code);
    List<PopulationGrowth> getPopulationGrowthRate(int yearOne, int yearTwo);
    List<PopulationData> getPopHistory();
    List<CountyPop> getCountiesPop();
}
