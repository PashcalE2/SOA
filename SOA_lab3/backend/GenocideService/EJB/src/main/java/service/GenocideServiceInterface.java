package service;

import exception.AppException;
import jakarta.ejb.Remote;


@Remote
public interface GenocideServiceInterface {
    Long countPopulation(Long id1, Long id2, Long id3) throws AppException;
    void moveToPoorest(Long cityId) throws AppException;
}
