package com.gussoft.userservice.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {

    private Long id;

    private String brand;
    private String model;

    private Long userId;

}
