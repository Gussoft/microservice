package com.gussoft.carservice.core.business;

import com.gussoft.carservice.core.exception.AppException;
import com.gussoft.carservice.core.models.Car;

import java.util.List;

public interface CarService {

    List<Car> findAll();

    List<Car> findByUserId(Long userId);

    Car findById(long id) throws AppException ;

    Car create(Car request);

    Car update(Car request, Long id) throws AppException;

    void delete(long id) throws AppException ;

}
