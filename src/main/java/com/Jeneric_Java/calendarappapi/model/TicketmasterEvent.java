package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TicketmasterEvent(String name, String url, Dates dates, Classifications[] classifications, Embedded _embedded, Image[] images) {
    public record Dates(Date start){
      public record Date(String localTime, String localDate){}
    }

    public record Classifications(Segment segment){
        public record Segment(String id){}
    }

    public record Embedded(Venue[] venues){
        public record Venue(String name, String postalCode){}
    }

    public record Image(String ratio, String url, int width, boolean fallback){}
}