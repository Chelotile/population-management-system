package dev.cheloti.populationms.service.impl;

import dev.cheloti.populationms.domain.CountyPopulation;
import dev.cheloti.populationms.domain.DataInformation;
import dev.cheloti.populationms.entity.County;
import dev.cheloti.populationms.repository.PopulationRepository;
import dev.cheloti.populationms.service.PopulationService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional
@RequiredArgsConstructor
public class PopulationServiceImpl implements PopulationService {
    private final PopulationRepository populationRepository;
    @Override
    public List<CountyPopulation> findCountiesWithPopulationsByYear(int year) {
        return populationRepository.getCountiesWithPopulationByYear(year);
    }

    @Override
    public List<CountyPopulation> findCountiesAbovePopulations(int year, int minPopulation) {
        return populationRepository.getCountiesAbovePopulation(year, minPopulation);
    }

    @Override
    public List<CountyPopulation> findCountyPopHistory(String name) {

        return populationRepository.getCountyPopHistory(name);
//                .orElseThrow(()-> new ResourceNotFoundException(name+ "county nopt found"));
    }

    @Override
    public List<DataInformation> findCountiesPopDensityByYear(int year) {

        var data = populationRepository.getCountiesWithPopulationByYear(year);

        return mapToDataInformation(data);
    }


    private List<DataInformation> mapToDataInformation(List<CountyPopulation> cp) {
//        return cp.stream().map(countyData->createDataInformation(countyData)).collect(Collectors.toList());
        return cp.stream().map(this::createDataInformation).collect(Collectors.toList());
    }
    private DataInformation createDataInformation(CountyPopulation cp) {
        DataInformation di = new DataInformation();
        di.setName(cp.name());
        di.setCode(cp.code());
        di.setPopDensity(calculatePopDensity(cp.population(),cp.geometry().getArea()));
        di.setGeometry(cp.geometry());
        return di;
    }

    private Double calculatePopDensity(Long pop, Double area) {
        if(area == null || area == 0) {
            throw new IllegalArgumentException("Area must be greater than zero");
        }
        return pop/area;
    }
}
