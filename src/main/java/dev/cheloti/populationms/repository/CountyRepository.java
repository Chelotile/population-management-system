package dev.cheloti.populationms.repository;

import dev.cheloti.populationms.domain.CountyInformation;

import java.util.List;
import java.util.Optional;

public interface CountyRepository {
    Optional<CountyInformation> getCountyById(int id);
    List<CountyInformation> getAllCounties();
    CountyInformation getCountyByName(String name);
    CountyInformation getCountyByCode(int code);
}
