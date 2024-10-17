package com.gussoft.userservice.core.business;

import com.gussoft.userservice.core.dtos.Bike;
import com.gussoft.userservice.core.dtos.Car;
import com.gussoft.userservice.core.exception.AppException;
import com.gussoft.userservice.core.models.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> findAll();

    User findById(long id) throws AppException ;

    User create(User user);

    User update(User user, Long id) throws AppException;

    void delete(long id) throws AppException ;

    List<Car> findAllCars(Long idUser);

    List<Bike> findAllBikes(Long idUser);

    Car saveCar(Car car);

    Bike saveBike(Bike bike);

    Map<String, Object> getUserAndVehicles(Long idUser);

}
