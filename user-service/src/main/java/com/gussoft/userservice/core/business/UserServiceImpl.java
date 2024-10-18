package com.gussoft.userservice.core.business;

import com.gussoft.userservice.core.clients.BikeFeignClient;
import com.gussoft.userservice.core.clients.CarFeignClient;
import com.gussoft.userservice.core.dtos.Bike;
import com.gussoft.userservice.core.dtos.Car;
import com.gussoft.userservice.core.exception.AppException;
import com.gussoft.userservice.core.models.User;
import com.gussoft.userservice.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RestTemplate template;
    private final CarFeignClient carClient;
    private final BikeFeignClient bikeClient;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(long id) throws AppException {
        Optional<User> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        return data.get();
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user, Long id) throws AppException {
        Optional<User> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        User update = data.get();
        update.setUsername(user.getUsername());
        update.setPassword(user.getPassword());
        return repository.save(update);

    }

    @Override
    public void delete(long id) throws AppException {
        Optional<User> data = repository.findById(id);
        if (!data.isPresent()) {
            throw new AppException("Resource Not Found", HttpStatus.NOT_FOUND.value());
        }

        repository.delete(data.get());
    }

    @Override
    public List<Car> findAllCars(Long idUser) {
        List<Car> cars = template.getForObject("http://car-service/v1/cars/byuser/" + idUser, List.class);
        return cars;
    }

    @Override
    public List<Bike> findAllBikes(Long idUser) {
        List<Bike> bikes = template.getForObject("http://bike-service/v1/bikes/byuser/" + idUser, List.class);
        return bikes;
    }

    @Override
    public Car saveCar(Car car) {
        return carClient.save(car);
    }

    @Override
    public Bike saveBike(Bike bike) {
        return bikeClient.save(bike);
    }

    @Override
    public Map<String, Object> getUserAndVehicles(Long idUser) {
        Map<String, Object> response = new HashMap<>();
        User user = repository.findById(idUser).orElse(null);
        if (user == null) {
            response.put("message", "User Not Found");
            return response;
        }
        response.put("user", user);
        List<Car> cars = carClient.getCarsByUserId(idUser);
        if (cars.isEmpty()) {
            response.put("cars", "Car Not Found");
        }
        response.put("cars", cars);
        List<Bike> bikes = bikeClient.getBikesByUserId(idUser);
        if (bikes.isEmpty()) {
            response.put("bikes", "Bike Not Found");
        }
        response.put("bikes", bikes);

        return response;
    }

}
