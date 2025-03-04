package configuration;

import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Startup
@Singleton
@Slf4j
public class ConsulRegistration {
    private static final String SERVICE_NAME = "city-service";
    private static final String SERVICE_ID = UUID.randomUUID().toString();

    private static final String SERVICE_ADDRESS = "localhost";
    private static final int SERVICE_PORT = 22601;
    private static final String CONSUL_URL = "http://localhost:8500";

    @PostConstruct
    public void registerService() {
        int registrationRetryCounter = 0;
        while (registrationRetryCounter < 5) {
            try {
                Consul consul = Consul.builder().withUrl(CONSUL_URL).build();

                Registration service = ImmutableRegistration.builder()
                        .id(SERVICE_ID)
                        .name(SERVICE_NAME)
                        .address(SERVICE_ADDRESS)
                        .port(SERVICE_PORT)
                        .build();
                consul.agentClient().register(service);
                log.info("Сервис зарегистрирован в Consul: {} (ID: {})", SERVICE_NAME, SERVICE_ID);
                break;
            } catch (Exception e) {
                registrationRetryCounter++;
                log.error("Ошибка регистрации в Consul, попытка {}: {}", registrationRetryCounter, e.getMessage());
                if (registrationRetryCounter == 5) {
                    log.error("Не удалось зарегистрировать сервис в Consul");
                }

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @PreDestroy
    public void deregisterService() {
        try {
            Consul consul = Consul.builder().withUrl(CONSUL_URL).build();
            consul.agentClient().deregister(SERVICE_ID);
            log.info("Сервис удален из Consul: {}", SERVICE_NAME+ " (ID: " + SERVICE_ID + ")");
        } catch (Exception e) {
            log.error("Ошибка удаления из Consul: {}", e.getMessage());
        }
    }


}