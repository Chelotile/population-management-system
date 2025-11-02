package dev.cheloti.populationms.service;

import dev.cheloti.populationms.domain.CountyPopulation;
import dev.cheloti.populationms.domain.DataInformation;

import java.util.List;

public interface PopulationService {

    List<CountyPopulation> findCountiesWithPopulationsByYear(int year);
    List<CountyPopulation> findCountiesAbovePopulations(int year, int minPopulation);
    List<CountyPopulation> findCountyPopHistory(String name);
    List<DataInformation> findCountiesPopDensityByYear(int year);

}
