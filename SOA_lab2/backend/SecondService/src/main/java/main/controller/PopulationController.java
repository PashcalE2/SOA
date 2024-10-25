package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.entity.dto.Count;
import main.exception.AppException;
import main.service.PopulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/genocide")
@AllArgsConstructor
@Slf4j
public class PopulationController {
    private final PopulationService populationService;

    @GetMapping("/count/{id1}/{id2}/{id3}")
    public ResponseEntity<?> countPopulation(@PathVariable Long id1, @PathVariable Long id2, @PathVariable Long id3) throws AppException {
        log.info("Запрос /count/{}/{}/{}", id1, id2, id3);
        return ResponseEntity.ok(new Count(populationService.countPopulation(id1, id2, id3)));
    }

    @PostMapping("/move-to-poorest/{cityId}")
    public ResponseEntity<?> moveToPoorest(@PathVariable Long cityId) throws AppException {
        log.info("Запрос /move-to-poorest/{}", cityId);
        populationService.moveToPoorest(cityId);
        return ResponseEntity.ok().build();
    }
}
