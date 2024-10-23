package service;

import entity.dto.Count;
import entity.dto.GroupById;
import entity.dto.GroupsById;
import entity.model.SortOrder;
import exception.AppException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import entity.model.City;
import entity.model.Climate;
import entity.repository.CityRepository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class CityService {
    private static final CityRepository cityRepository = new CityRepository();

    public List<City> get(String filterFields, String filterValues, String sortFields, SortOrder sortOrder, Integer page, Integer size) throws AppException {
        List<City> citiesList = new ArrayList<>(cityRepository.findAll());
        filter(citiesList, filterFields, filterValues);
        sort(citiesList, sortFields, sortOrder);
        paginate(citiesList, page, size);

        return citiesList;
    }

    public List<City> get(String sortFields, SortOrder sortOrder, Integer page, Integer size) throws AppException {
        List<City> citiesList = new ArrayList<>(cityRepository.findAll());
        sort(citiesList, sortFields, sortOrder);
        paginate(citiesList, page, size);

        return citiesList;
    }

    public City add(City city) throws AppException {
        if (!city.isValidRequest()) {
            throw new AppException(Response.Status.BAD_REQUEST, "Неправильные поля / значения");
        }

        city.setCreationDate(ZonedDateTime.now());
        cityRepository.add(city);

        return city;
    }

    public City findById(Long id) throws AppException {
        City city = cityRepository.findById(id);
        if (city == null) {
            throw new AppException(Response.Status.NOT_FOUND, "Нет города с таким ID");
        }
        return city;
    }

    public City updateCity(Long id, City city) throws AppException {
        if (!city.isValidRequest()) {
            throw new AppException(Response.Status.BAD_REQUEST, "Неправильные поля / значения");
        }

        return cityRepository.update(id, city);
    }

    public void deleteCity(Long id) throws AppException {
        cityRepository.delete(id);
    }

    public void deleteByGovernor(String governorName) throws AppException {
        if (!cityRepository.deleteByGovernor(governorName)) {
            throw new AppException(Response.Status.NOT_ACCEPTABLE, "Нет городов с таким губернатором");
        }
    }

    public GroupsById groupById() {
        return new GroupsById(cityRepository.findAll().stream().map(city -> new GroupById(city.getId(), 1L)).collect(Collectors.toList()));
    }

    public Count countByClimate(Climate climate) {
        return new Count(cityRepository.findAll().stream().filter(city -> city.getClimate().equals(climate)).count());
    }

    private void filter(List<City> citiesList, String filterFields, String filterValues) throws AppException {
        String[] filterFieldsArray = filterFields.split(",");
        String[] filterValuesArray = filterValues.split(",");
        if (filterFieldsArray.length != filterValuesArray.length) {
            throw new AppException(Response.Status.BAD_REQUEST, "Количество полей для фильтрации не совпадает с количеством значений"); // TODO: status
        }

        try {
            citiesList.removeIf((city) -> {
                for (int i = 0; i < filterFieldsArray.length; i++) {
                    if (!city.fieldEquals(filterFieldsArray[i], filterValuesArray[i])) {
                        return true;
                    }
                }
                return false;
            });
        }
        catch (IllegalArgumentException | DateTimeParseException e) {
            throw new AppException(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    private void sort(List<City> citiesList, String sortFields, SortOrder sortOrder) throws AppException {
        try {
            citiesList.sort((city1, city2) -> {
                for (String field : sortFields.split(",")) {
                    int compare = city1.compareBy(city2, field);
                    if (compare != 0) {
                        return sortOrder.equals(SortOrder.DESCENDING) ? -compare : compare;
                    }
                }
                return 0;
            });
        }
        catch (IllegalArgumentException e) {
            throw new AppException(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    private void paginate(List<City> citiesList, Integer page, Integer size) throws AppException {
        if (page < 0 || size < 0) {
            throw new AppException(Response.Status.BAD_REQUEST, "Номер и размер страницы должны быть больше 0");
        }

        int citiesCount = citiesList.size();
        int start = Math.min(page * size, citiesCount);
        int end = Math.min((page + 1) * size, citiesCount);
        citiesList = citiesList.subList(start, end);
    }
}

