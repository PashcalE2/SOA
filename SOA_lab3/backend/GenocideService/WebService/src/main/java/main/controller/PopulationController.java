package main.controller;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import entity.dto.Count;
import exception.AppException;
import service.GenocideServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/genocide")
@NoArgsConstructor
@Slf4j
public class PopulationController {
    @Autowired
    private GenocideServiceInterface populationService;

    @GetMapping("/count/{id1}/{id2}/{id3}")
    public ResponseEntity<?> countPopulation(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2, @PathVariable("id3") Long id3) throws AppException {
        log.info("Запрос /count/{}/{}/{}", id1, id2, id3);
        return ResponseEntity.ok(new Count(populationService.countPopulation(id1, id2, id3)));
    }

    @PostMapping("/move-to-poorest/{cityId}")
    public ResponseEntity<?> moveToPoorest(@PathVariable("cityId") Long cityId) throws AppException {
        log.info("Запрос /move-to-poorest/{}", cityId);
        populationService.moveToPoorest(cityId);
        return ResponseEntity.ok().build();
    }
}
