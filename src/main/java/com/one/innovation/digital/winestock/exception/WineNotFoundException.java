package com.one.innovation.digital.winestock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WineNotFoundException extends Exception {

    public WineNotFoundException(String wineName) {
        super(String.format("Wine with name %s not found in the system.", wineName));
    }

    public WineNotFoundException(Long id) {
        super(String.format("Wine with id %s not found in the system.", id));
    }
}