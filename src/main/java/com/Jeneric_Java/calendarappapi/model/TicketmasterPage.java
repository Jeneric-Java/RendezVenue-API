package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TicketmasterPage(Embedded _embedded, Page page) {
    public record Embedded(TicketmasterEvent[] events){}

    public record Page(int size, int totalElements, int totalPages, int number){}
}
