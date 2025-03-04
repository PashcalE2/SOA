package service;

import adapter.CityServiceAdapter;
import entity.dto.SortOrder;
import entity.model.City;
import exception.AppException;
import jakarta.jws.*;
import jakarta.jws.soap.SOAPBinding;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@WebService(
        name = "GenocideService",
        endpointInterface = "service.GenocideServiceInterface",
        targetNamespace = "http://genocide.lab4.soa/")
public class GenocideService implements GenocideServiceInterface {
    private final CityServiceAdapter cityServiceAdapter = new CityServiceAdapter();

    public Long countPopulation(Long id1, Long id2, Long id3) throws AppException {
        City city1 = cityServiceAdapter.getById(id1);
        City city2 = cityServiceAdapter.getById(id2);
        City city3 = cityServiceAdapter.getById(id3);

        return ((long)city1.getPopulation() + (long)city2.getPopulation() + (long)city3.getPopulation());
    }

    public void moveToPoorest(Long cityId) throws AppException {
        City mainCity = cityServiceAdapter.getById(cityId);
        City poorestCity = cityServiceAdapter.getAllSortedPaginated("population", SortOrder.ASCENDING, 0, 1).getCity().get(0);

        if (mainCity.equals(poorestCity)) {
            throw new AppException(Response.Status.FORBIDDEN, String.format("Город с таким ID уже с самым низким уровнем жизни: %s", cityId));
        }

        log.info("Население было: откуда ({}), куда ({})", mainCity.getPopulation(), poorestCity.getPopulation());
        poorestCity.setPopulation(poorestCity.getPopulation() + mainCity.getPopulation() - 1);
        mainCity.setPopulation(1);
        log.info("Население стало: откуда ({}), куда ({})", mainCity.getPopulation(), poorestCity.getPopulation());

        try {
            cityServiceAdapter.putById(poorestCity.getId(), poorestCity);
            cityServiceAdapter.putById(mainCity.getId(), mainCity);
            log.info("Города (poorest: {}, main: {}) успешно обновлены", poorestCity.getId(), mainCity.getId());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
