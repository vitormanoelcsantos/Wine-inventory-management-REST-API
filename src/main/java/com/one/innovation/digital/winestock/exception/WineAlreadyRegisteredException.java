package com.one.innovation.digital.winestock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WineAlreadyRegisteredException extends Exception{

    public WineAlreadyRegisteredException(String wineName) {
        super(String.format("Wine with name %s already registered in the system.", wineName));
    }
}