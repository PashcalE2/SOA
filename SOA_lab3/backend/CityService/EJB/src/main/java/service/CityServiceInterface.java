package service;

import entity.dto.Count;
import entity.dto.GroupsById;
import entity.dto.SortOrder;
import entity.model.City;
import entity.model.Climate;
import exception.AppException;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface CityServiceInterface {
    List<City> get(String filterFields, String filterValues, String sortFields, SortOrder sortOrder, Integer page, Integer size) throws AppException;
    List<City> get(String sortFields, SortOrder sortOrder, Integer page, Integer size) throws AppException;
    City add(City city) throws AppException;
    City findById(Long id) throws AppException;
    City updateCity(Long id, City city) throws AppException;
    void deleteCity(Long id) throws AppException;
    void deleteByGovernor(String governorName) throws AppException;
    GroupsById groupById();
    Count countByClimate(Climate climate);
}
