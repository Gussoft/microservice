package com.gussoft.carservice.core.exception;

import lombok.Getter;

@Getter
public class AppException extends Exception {

    private int status;

    public AppException(String message, int status) {
        super(message);
        this.status = status;
    }

}
