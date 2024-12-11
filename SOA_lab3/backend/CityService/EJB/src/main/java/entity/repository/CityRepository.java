package entity.repository;

import entity.model.City;
import entity.model.Human;
import exception.AppException;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    }

    public City update(Long id, City city) {
        City old = cities.remove(id);
        city.setId(id);
        city.setCreationDate(old.getCreationDate());
        cities.put(id, city);
        return city;
    }

    public void delete(Long id) throws AppException {
        if (cities.remove(id) == null) {
            throw new AppException(Response.Status.NOT_FOUND, "Города с таким ID нет");
        }
    }

    public boolean deleteByGovernor(String governorName) throws AppException {
        Human governor = findAll()
                .stream()
                .filter(city -> city.getGovernor().getName().equals(governorName))
                .findFirst()
                .map(City::getGovernor)
                .orElseThrow(() -> new AppException(Response.Status.NOT_FOUND, String.format("Губернатор с таким именем не найден: %s", governorName)));

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
