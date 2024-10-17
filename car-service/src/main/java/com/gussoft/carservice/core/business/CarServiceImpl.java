package com.gussoft.carservice.core.business;

import com.gussoft.carservice.core.exception.AppException;
import com.gussoft.carservice.core.models.Car;
import com.gussoft.carservice.core.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository repository;


    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Car> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Car findById(long id) throws AppException {
        Optional<Car> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        return data.get();
    }

    @Override
    public Car create(Car car) {
        return repository.save(car);
    }

    @Override
    public Car update(Car car, Long id) throws AppException {
        Optional<Car> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        Car update = data.get();
        update.setBrand(car.getBrand());
        return repository.save(update);

    }

    @Override
    public void delete(long id) throws AppException {
        Optional<Car> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        repository.delete(data.get());
    }
}
