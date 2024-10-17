package com.gussoft.userservice.core.clients;

import com.gussoft.userservice.core.dtos.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002/v1")
public interface CarFeignClient {

    @PostMapping("/cars")
    Car save(@RequestBody Car car);

    @GetMapping("/cars/byuser/{userId}")
    List<Car> getCarsByUserId(@PathVariable("userId") Long userId);

}
