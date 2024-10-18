package com.gussoft.userservice.core.clients;

import com.gussoft.userservice.core.dtos.Bike;
import com.gussoft.userservice.core.dtos.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "bike-service") //, url = "http://localhost:8003/v1")
public interface BikeFeignClient {

    @PostMapping("/v1/bikes")
    Bike save(@RequestBody Bike bike);

    @GetMapping("/v1/bikes/byuser/{userId}")
    List<Bike> getBikesByUserId(@PathVariable("userId") Long userId);

}
