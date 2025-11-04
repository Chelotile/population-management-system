package dev.cheloti.populationms.service.impl;

import dev.cheloti.populationms.domain.*;
import dev.cheloti.populationms.dto.PopulationDTO;
import dev.cheloti.populationms.dtoMapper.PopulationDTOMapper;
import dev.cheloti.populationms.entity.County;
import dev.cheloti.populationms.entity.PopulationData;
import dev.cheloti.populationms.enumeration.Category;
import dev.cheloti.populationms.repository.PopulationRepository;
import dev.cheloti.populationms.service.PopulationService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static dev.cheloti.populationms.enumeration.Category.*;

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

    @Override
    public List<GrowthCategory> findPopulationGrowthCategory(int yearOne, int yearTwo) {

        if(yearOne>yearTwo){
            throw new IllegalArgumentException("Years must be greater than the Years");
        }
        var data = populationRepository.getPopulationGrowthRate(yearOne, yearTwo);

        return  mapToGrowthCategory(data);
    }

    @Override
    public List<PopulationDTO> findPopHistory() {
//        var popHistory = populationRepository.getPopHistory();
//        var data = new ArrayList<PopulationDTO>();
//        for (var pop: popHistory) {
//            PopulationDTO populationData = PopulationDTOMapper.fromPopulation(pop);
//            data.add(populationData);
//        }
//        return data;
        return populationRepository.getPopHistory()
                .stream().map(PopulationDTOMapper::fromPopulation)
                .collect(Collectors.toList());
    }

    @Override
    public List<CountyPop> findCountyPop(String name) {
        return List.of();
    }

    @Override
    public List<CountyPop> findCountiesPopHistory() {
        return populationRepository.getCountiesPop();

    }

    @Override
    public Optional<CountyPop> findCountyPopHistoryByCode(int code) {
        return populationRepository.getCountiesPop()
                .stream().filter(p -> p.getCode().equals(code)).findFirst();
    }


    @Override
    public List<DataInformation> findCountyPopDensityByCode(int code) {
        var data = populationRepository.getCountyPopHistoryByCode(code);
        return mapToDataInformation(data);
    }

    @Override
    public List<PopulationGrowth> findPopulationGrowthRate(int yearOne, int yearTwo) {
        if(yearOne > yearTwo) {
            throw new IllegalArgumentException("Start year must be greater than or equal to end year");
        }
        return populationRepository.getPopulationGrowthRate(yearOne, yearTwo);
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
//
//    private CountyPop createCountyPop(CountyPopulation cp) {
//        CountyPop pop = new CountyPop();
//        pop.setCode(cp.code());
//        pop.setName(cp.name());
//        pop.setPopulation();
//    }




    private List<GrowthCategory> mapToGrowthCategory(List<PopulationGrowth> cp) {

        return cp.stream().map(this::createGrowthCategory).collect(Collectors.toList());
    }



    private GrowthCategory createGrowthCategory(PopulationGrowth growth) {
         GrowthCategory gc = new GrowthCategory();
         gc.setName(growth.name());
         gc.setCode(growth.code());
         gc.setCategory(checkCategory(growth.growthRate()));
         gc.setArea(growth.geometry().getArea());
         gc.setGeometry(growth.geometry());
         return gc;
    }

    private Category checkCategory(Double rate) {

       return switch(rate) {
           case Double i when i < 0.00 -> DECLINING;
           case Double i when i >= 0.00 && i < 5.00 -> STAGNANT;
           case Double i when  i >= 5.00 && i< 12.00 -> GROWING;
           case Double i  when i >= 12.00 -> BOOMING;
           case null -> throw new IllegalArgumentException("Invalid category");
           default -> INVALID;
        };
    }
}
