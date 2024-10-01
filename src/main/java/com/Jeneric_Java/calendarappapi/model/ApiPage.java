package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiPage(Embedded _embedded) {
    public record Embedded(ApiEvent[] events){}
}
