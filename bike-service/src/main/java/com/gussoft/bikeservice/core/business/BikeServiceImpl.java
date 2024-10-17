package com.gussoft.bikeservice.core.business;

import com.gussoft.bikeservice.core.exception.AppException;
import com.gussoft.bikeservice.core.models.Bike;
import com.gussoft.bikeservice.core.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BikeServiceImpl implements BikeService {

    private final BikeRepository repository;


    @Override
    public List<Bike> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Bike> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Bike findById(long id) throws AppException {
        Optional<Bike> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        return data.get();
    }

    @Override
    public Bike create(Bike Bike) {
        return repository.save(Bike);
    }

    @Override
    public Bike update(Bike Bike, Long id) throws AppException {
        Optional<Bike> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        Bike update = data.get();
        update.setBrand(Bike.getBrand());
        return repository.save(update);

    }

    @Override
    public void delete(long id) throws AppException {
        Optional<Bike> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        repository.delete(data.get());
    }
}
