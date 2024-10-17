package com.gussoft.carservice.integration.integration;

import com.gussoft.carservice.core.business.CarService;
import com.gussoft.carservice.core.exception.AppException;
import com.gussoft.carservice.core.models.Car;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> findAll() {
        List<Car> data = service.findAll();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/cars/byuser/{id}")
    public ResponseEntity<List<Car>> findByUserId(@PathVariable Long id) {
        List<Car> data = service.findByUserId(id);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id) throws AppException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> create(@RequestBody Car request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> update(@PathVariable Long id, @RequestBody Car request) throws AppException {
        return ResponseEntity.ok(service.update(request, id));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws AppException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
