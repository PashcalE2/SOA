package entity.repository;

import exception.AppException;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import entity.model.City;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@NoArgsConstructor
@Slf4j
public class CityRepository {
    private final Map<Long, City> cities = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public Collection<City> findAll() {
        return cities.values();
    }

    public City findById(Long id) {
        return cities.get(id);
    }

    public void add(City city) {
        Long id = generateUniqueId();
        city.setId(id);

        cities.put(id, city);
        log.info("Total cities now: {}", cities.size());
    }

    public City update(Long id, City city) {
        city.setId(id);
        return cities.replace(id, city);
    }

    public void delete(Long id) throws AppException {
        if (cities.remove(id) == null) {
            throw new AppException(Response.Status.NOT_FOUND, "Города с таким ID нет");
        }
    }

    public boolean deleteByGovernor(String governorName) {
        List<Long> citiesToDelete = new ArrayList<>();
        for (City city : cities.values()) {
            if (city.getGovernor().getName().equals(governorName)) {
                citiesToDelete.add(city.getId());
            }
        }

        if (citiesToDelete.isEmpty()) {
            return false;
        }

        for (Long id : citiesToDelete) {
            cities.remove(id);
        }

        return true;
    }

    private Long generateUniqueId() {
        return counter.incrementAndGet();
    }
}
