package com.gussoft.userservice.integration.integration;

import com.gussoft.userservice.core.business.UserService;
import com.gussoft.userservice.core.dtos.Bike;
import com.gussoft.userservice.core.dtos.Car;
import com.gussoft.userservice.core.exception.AppException;
import com.gussoft.userservice.core.models.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> data = service.findAll();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) throws AppException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(service.create(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) throws AppException {
        return ResponseEntity.ok(service.update(user, id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws AppException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackGetCars")
    @GetMapping("/users/{userId}/cars")
    public ResponseEntity<List<Car>> getCars(@PathVariable Long userId) throws AppException {
        User user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }

        List<Car> cars = service.findAllCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackGetBikes")
    @GetMapping("/users/{userId}/bikes")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable Long userId) throws AppException {
        User user = service.findById(userId);
        if (user == null) {
            return ResponseEntity.noContent().build();
        }

        List<Bike> bikes = service.findAllBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackSaveCar")
    @PostMapping("/users/{userId}/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car request) {
        Car car = service.saveCar(request);
        return ResponseEntity.ok(car);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackSaveBike")
    @PostMapping("/users/{userId}/bikes")
    public ResponseEntity<Bike> createBikes(@RequestBody Bike request) {
        Bike bike = service.saveBike(request);
        return ResponseEntity.ok(bike);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallbackGetAll")
    @GetMapping("/users/{userId}/vehicles")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable Long userId) {
        Map<String, Object> data = service.getUserAndVehicles(userId);
        return ResponseEntity.ok(data);
    }

    private ResponseEntity<List<Car>> fallbackGetCars(@PathVariable Long userId, RuntimeException e) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "the user " + userId + " is temporarily busy with his Cart, try again later.");
        return new ResponseEntity(data, HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallbackGetBikes(@PathVariable Long userId, RuntimeException e) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "the user " + userId + " is temporarily busy with his Bike, try again later.");
        return new ResponseEntity(data, HttpStatus.OK);
    }

    private ResponseEntity<Car> fallbackSaveCar(@RequestBody Car request, RuntimeException e) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "the user " + request.getUserId() + " is temporarily out of money to buy a Car, try again later.");
        return new ResponseEntity(data, HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallbackSaveBike(@RequestBody Bike request, RuntimeException e) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "the user " + request.getUserId() + " is temporarily out of money to buy a Bike, try again later.");
        return new ResponseEntity(data, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallbackGetAll(@PathVariable Long userId, RuntimeException e) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "the user " + userId + " is temporarily without vehicles, try again later.");
        return new ResponseEntity(data, HttpStatus.OK);
    }

}
