package main.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genocide")
@AllArgsConstructor
public class PopulationController {
    @GetMapping("/count/{id1}/{id2}/{id3}")
    public String countPopulation(@PathVariable Long id1, @PathVariable Long id2, @PathVariable Long id3) {
        return String.format("%d %d %d", id1, id2, id3);
    }

    @PostMapping("/move-to-poorest/{id}")
    public String moveToPoorest(@PathVariable Long id) {
        return String.format("%d", id);
    }
}
