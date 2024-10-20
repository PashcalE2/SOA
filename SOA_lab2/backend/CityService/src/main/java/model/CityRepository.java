package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class CityRepository {
    private final Map<Long, City> cities = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public CityRepository() {
    }

    public Collection<City> getCities() {
        return cities.values();
    }

    public void addCity(City city) {
        cities.put(city.getId(), city);
    }

    public Long generateUniqueId() {
        return counter.addAndGet(1);
    }
}
