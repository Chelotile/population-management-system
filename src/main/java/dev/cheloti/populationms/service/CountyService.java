package dev.cheloti.populationms.service;

import dev.cheloti.populationms.domain.CountyInformation;

import java.util.List;
import java.util.Optional;

public interface CountyService {
    Optional<CountyInformation> findCountyById(int id);
    List<CountyInformation> findAllCounties();
    CountyInformation findCountyByName(String name);
    CountyInformation findCountyByCode(int code);
}
