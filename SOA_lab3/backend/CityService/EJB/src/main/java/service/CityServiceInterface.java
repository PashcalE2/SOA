package service;

import entity.dto.Cities;
import entity.dto.Count;
import entity.dto.GroupsById;
import entity.dto.SortOrder;
import entity.model.City;
import entity.model.Climate;
import exception.AppException;
import jakarta.ejb.Remote;

@Remote
public interface CityServiceInterface {
    Cities get(String filterFields, String filterValues, String sortFields, SortOrder sortOrder, Integer page, Integer size) throws AppException;
    Cities get(String sortFields, SortOrder sortOrder, Integer page, Integer size) throws AppException;
    City add(City city) throws AppException;
    City findById(Long id) throws AppException;
    City updateCity(Long id, City city) throws AppException;
    void deleteCity(Long id) throws AppException;
    void deleteByGovernor(String governorName) throws AppException;
    GroupsById groupById();
    Count countByClimate(Climate climate);
}
