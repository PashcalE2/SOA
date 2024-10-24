package main.service;

import lombok.extern.slf4j.Slf4j;
import main.adapter.CityServiceAdapter;
import main.entity.dto.SortOrder;
import main.entity.model.City;


@Slf4j
public class PopulationService {
    private final CityServiceAdapter cityServiceAdapter = new CityServiceAdapter();

    public Long countPopulation(Long id1, Long id2, Long id3) {
        City city1 = cityServiceAdapter.getById(id1);
        City city2 = cityServiceAdapter.getById(id2);
        City city3 = cityServiceAdapter.getById(id3);

        return ((long)city1.getPopulation() + (long)city2.getPopulation() + (long)city3.getPopulation());
    }

    public void moveToPoorest(Long cityId) {
        City mainCity = cityServiceAdapter.getById(cityId);
        City poorestCity = cityServiceAdapter.getAllSortedPaginated("population", SortOrder.ASCENDING, 0, 1).getCity().get(0);

        if (mainCity.equals(poorestCity)) {
            log.info("Этот город уже с самым низким уровнем жизни");
            return;
        }

        log.info("Население было: откуда ({}), куда ({})", mainCity.getPopulation(), poorestCity.getPopulation());
        poorestCity.setPopulation(poorestCity.getPopulation() + mainCity.getPopulation());
        mainCity.setPopulation(0);
        log.info("Население стало: откуда ({}), куда ({})", mainCity.getPopulation(), poorestCity.getPopulation());

        try {
            cityServiceAdapter.putById(poorestCity.getId(), poorestCity);
            cityServiceAdapter.putById(mainCity.getId(), mainCity);
            log.info("Объекты (id1: {}, id2: {}) успешно обновлены", poorestCity.getId(), mainCity.getId());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
