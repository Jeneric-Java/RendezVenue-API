package com.Jeneric_Java.calendarappapi.model;

public record ApiEvent() {
    public record Dates(Date start){
        public record Date(String localTime, String localData){}
    }

    public record Classifications(Segment segment){
        public record Segment(String name){}
    }
}
