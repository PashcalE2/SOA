package main;

import service.GenocideService;
import service.GenocideServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfiguration {
    @Bean
    public GenocideServiceInterface populationService() {
        return new GenocideService();
    }
}
