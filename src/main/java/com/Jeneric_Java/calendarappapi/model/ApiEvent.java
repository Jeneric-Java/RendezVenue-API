package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiEvent(String name, String url, Dates dates, Classifications[] classifications, Embedded _embedded) {
    public record Dates(Date start){
        public record Date(String localTime, String localData){}
    }

    public record Classifications(Segment segment){
        public record Segment(String name){}
    }

    public record Embedded(Venue[] venues){
        public record Venue(String name, String postalCode){}
    }
}
