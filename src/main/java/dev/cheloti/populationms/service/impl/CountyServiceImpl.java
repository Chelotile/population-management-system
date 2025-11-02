package dev.cheloti.populationms.service.impl;

import dev.cheloti.populationms.domain.CountyInformation;
import dev.cheloti.populationms.repository.CountyRepository;
import dev.cheloti.populationms.service.CountyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {
    private final CountyRepository countyRepository;

    @Override
    public Optional<CountyInformation> findCountyById(int id) {

        return countyRepository.getCountyById(id);
    }

    @Override
    public List<CountyInformation> findAllCounties() {
        return List.of();
    }

    @Override
    public CountyInformation findCountyByName(String name) {
        return null;
    }

    @Override
    public CountyInformation findCountyByCode(int code) {
        return null;
    }
}
