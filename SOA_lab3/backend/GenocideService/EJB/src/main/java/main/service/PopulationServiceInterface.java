package main.service;

import jakarta.ejb.Remote;
import main.exception.AppException;


@Remote
public interface PopulationServiceInterface {
    Long countPopulation(Long id1, Long id2, Long id3) throws AppException;
    void moveToPoorest(Long cityId) throws AppException;
}
