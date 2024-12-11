package main;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
@Setter
public class AppConfiguration {
    @Value("${cities.endpoint}")
    public String baseEndpoint;
}
