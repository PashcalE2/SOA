package main;

import main.service.PopulationService;
import main.service.PopulationServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfiguration {
    @Bean
    public PopulationServiceInterface populationService() {
        return new PopulationService();
    }
}
