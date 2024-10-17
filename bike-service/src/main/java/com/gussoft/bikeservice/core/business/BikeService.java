package com.gussoft.bikeservice.core.business;

import com.gussoft.bikeservice.core.exception.AppException;
import com.gussoft.bikeservice.core.models.Bike;
import java.util.List;

public interface BikeService {

    List<Bike> findAll();

    List<Bike> findByUserId(Long userId);

    Bike findById(long id) throws AppException;

    Bike create(Bike request);

    Bike update(Bike request, Long id) throws AppException;

    void delete(long id) throws AppException ;

}
