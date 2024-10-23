package model.repository;

import lombok.extern.slf4j.Slf4j;
import model.City;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
public class CityRepository {
    private final Map<Long, City> cities = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public CityRepository() {
    }

    public Collection<City> findAll() {
        return cities.values();
    }

    public City findById(Long id) {
        return cities.get(id);
    }

    public void addCity(City city) {
        Long id = generateUniqueId();
        city.setId(id);

        cities.put(id, city);
        log.info("Total cities now: {}", cities.size());
    }

    public City updateCity(Long id, City city) {
        city.setId(id);
        return cities.replace(id, city);
    }

    public void deleteCity(Long id) {
        cities.remove(id);
    }

    public void deleteByGovernor(Long governorId) {
        List<Long> citiesToDelete = new ArrayList<>();
        for (City city : cities.values()) {
            if (city.getGovernor().getAge() == governorId) {
                citiesToDelete.add(city.getId());
            }
        }

        for (Long id : citiesToDelete) {
            cities.remove(id);
        }
    }

    private Long generateUniqueId() {
        return counter.incrementAndGet();
    }
}
