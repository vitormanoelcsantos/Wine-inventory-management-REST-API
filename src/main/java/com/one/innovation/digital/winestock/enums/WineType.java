package com.one.innovation.digital.winestock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WineType {

    WHITEWINE("White wine"),
    REDWINE("Red wine"),
    ROSEWINE("Rose wine"),
    SWEETWINE("Sweet wine"),
    SPARKLING("Sparkling wine");

    private final String description;
}