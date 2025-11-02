package dev.cheloti.populationms.controller;

import dev.cheloti.populationms.domain.Response;
import dev.cheloti.populationms.service.CountyService;
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
@RequestMapping("/countiesInfo")
@RequiredArgsConstructor
@Slf4j
public class CountyController {

    private final CountyService countyService;

    @GetMapping("/by-id")
    public ResponseEntity<Response> getCountyById(@RequestParam int id) {
        log.info("Retrieving county  id number: {}", id);

        try {
            var county = countyService.findCountyById(id);

            return ResponseEntity.ok().body(
                    new Response(
                            "County retrieved",
                            HttpStatus.OK,
                            HttpStatus.OK.value(),
                            Map.of("County", county)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
