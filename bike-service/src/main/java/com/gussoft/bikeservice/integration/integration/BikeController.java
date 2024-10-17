package com.gussoft.bikeservice.integration.integration;

import java.util.List;

import com.gussoft.bikeservice.core.business.BikeService;
import com.gussoft.bikeservice.core.exception.AppException;
import com.gussoft.bikeservice.core.models.Bike;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService service;

    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> findAll() {
        List<Bike> data = service.findAll();
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/bikes/byuser/{id}")
    public ResponseEntity<List<Bike>> findByUserId(@PathVariable Long id) {
        List<Bike> data = service.findByUserId(id);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/bikes/{id}")
    public ResponseEntity<Bike> findById(@PathVariable Long id) throws AppException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/bikes")
    public ResponseEntity<Bike> create(@RequestBody Bike request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/bikes/{id}")
    public ResponseEntity<Bike> update(@PathVariable Long id, @RequestBody Bike request) throws AppException {
        return ResponseEntity.ok(service.update(request, id));
    }

    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws AppException {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
