package model;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public class CityRepository {
    private final Map<Long, City> cities = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public CityRepository() {
    }

    public Map<Long, City> getCities() {
        return cities;
    }

    public void addCity(City city) {
        Long id = generateUniqueId();
        city.setId(id);

        cities.put(id, city);
        log.info("Total cities now: {}", cities.size());
    }

    private Long generateUniqueId() {
        return counter.incrementAndGet();
    }
}
