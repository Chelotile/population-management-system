package dev.cheloti.populationms.controller;

import dev.cheloti.populationms.domain.Response;
import dev.cheloti.populationms.service.PopulationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/counties")
@RequiredArgsConstructor
@Slf4j
public class PopulationController {
    private final PopulationService populationService;

    @GetMapping("/population")
    public ResponseEntity<Response> getPopulationByYear(@RequestParam("year") int year) {

        log.info("Retrieving population for year {}", year);
        try {
            var populationByYear = populationService.findCountiesWithPopulationsByYear(year);

            return ResponseEntity.ok().body(
                    new Response(
                            "Population data retrieved",
                            HttpStatus.OK,
                            HttpStatus.OK.value(),
                            Map.of("population data",populationByYear)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/min")
    public ResponseEntity<Response> getPopulationAbove(@RequestParam("year") int year, @RequestParam("minPopulation") int minPopulation) {
        log.info("Retrieving population for year {} and minPop {}", year, minPopulation);
        try{
            var popData = populationService.findCountiesAbovePopulations(year, minPopulation);
            return ResponseEntity.ok().body(
                    new Response(
                            "Minimum population retrieved",
                            HttpStatus.OK,
                            HttpStatus.OK.value(),
                            Map.of("Minimum population data",popData)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity<Response>getCountyPopHistory(@RequestParam("name") String name){

        String theName= switch (name){
            case String s when (s.equals(s.toUpperCase()))->name.toLowerCase().substring(0,1).toUpperCase()+name.substring(1);
            case String s when (s.equals(s.toLowerCase()))->name.substring(0,1).toUpperCase()+name.substring(1);
            default -> name;

        };
        log.info("Retrieving population history for {} county", theName);

        try {
            var popData = populationService.findCountyPopHistory(theName);

            return ResponseEntity.ok().body(
                    new Response(
                            "Population history",
                            HttpStatus.OK,
                            HttpStatus.OK.value(),
                            Map.of("population history",popData)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/density")
    public ResponseEntity<Response> getPopDensityByYear(@RequestParam("year") int year){

        log.info("Retrieving populationDensity for year {}", year);
        try {
            var popDensity = populationService.findCountiesPopDensityByYear(year);

            return ResponseEntity.ok().body(
                    new Response(
                            "Population Density",
                            HttpStatus.OK,
                            HttpStatus.OK.value(),
                            Map.of("population density",popDensity)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/rate")
    public ResponseEntity<Response> getPopGrowthRate(@RequestParam int yearOne, @RequestParam int yearTwo){
        log.info("Retrieving populationGrowthRate btn year {} and {}", yearOne, yearTwo);

        try {
            var rate = populationService.findPopulationGrowthRate(yearOne, yearTwo);
            return ResponseEntity.ok().body(
                    new Response(
                            "pop growth rate",
                            HttpStatus.OK,
                            HttpStatus.OK.value(),
                            Map.of("population growth rate",rate)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/category")
    public ResponseEntity<Response> getGrowthRateCategory(@RequestParam int yearOne, @RequestParam int yearTwo){

        var category = populationService.findPopulationGrowthCategory(yearOne, yearTwo);
        return ResponseEntity.ok().body(
                new Response(
                        "growth rate category",
                        HttpStatus.OK,
                        HttpStatus.OK.value(),
                        Map.of("population growth rate category",category)
                )
        );
    }

    @GetMapping("/code")
    public ResponseEntity<Response> getPopHistory(@RequestParam("code") int code){

        var history = populationService.findCountyPopHistoryByCode(code);

        return ResponseEntity.ok().body(
                new Response(
                        "Pop History",
                        HttpStatus.OK,
                        HttpStatus.OK.value(),
                        Map.of("population history",history)
                )
        );
    }

    @GetMapping("/data")
    public ResponseEntity<Response> getPopHistoryWithGeom(){
        return ResponseEntity.ok().body(
                new Response(
                        "Pop History",
                        HttpStatus.OK,
                        HttpStatus.OK.value(),
                        Map.of("population history",populationService.findCountiesPopHistory())
                )
        );
    }


}
