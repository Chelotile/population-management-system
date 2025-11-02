package dev.cheloti.populationms.repository;

import dev.cheloti.populationms.domain.CountyPopulation;

import java.util.List;

public interface PopulationRepository {
    List<CountyPopulation> getCountiesWithPopulationByYear(int year);
    List<CountyPopulation> getCountiesAbovePopulation(int year, int minPopulation);
    List<CountyPopulation> getCountyPopHistory(String name);
}
