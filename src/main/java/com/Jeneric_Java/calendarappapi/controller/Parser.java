package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.ApiEvent;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.Time;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.stream.Stream;

@Controller
public class Parser {

    public Event parseEvent(ApiEvent input) throws ParseException {
        if (input == null) throw new IllegalArgumentException("Event cannot be null!");
        if (input.name() == null || input.url() == null || input.dates() == null || input.classifications() == null || input.classifications().length == 0 || input.classifications()[0] == null || input.classifications()[0].segment() == null || input.classifications()[0].segment().name() == null || input._embedded() == null || input._embedded().venues() == null || input._embedded().venues().length == 0 || input._embedded().venues()[0] == null || input._embedded().venues()[0].name() == null || input._embedded().venues()[0].postalCode() == null) throw new IllegalArgumentException();

        String description = input.name() + " @ " + input._embedded().venues()[0].name();

        return new Event(
                null,
                input.name(),
                description,
                input._embedded().venues()[0].postalCode(),
                input.url(),
                input.classifications()[0].segment().name(),
                parseTime(input.dates().start()),
                null
        );
    }

    public Time parseTime(ApiEvent.Dates.Date input) throws ParseException {
        if (input == null || input.localTime() == null || input.localDate() == null) throw new IllegalArgumentException("Error while parsing event date/time! Date/Time cannot be null!");

        String[] date = input.localDate().split("-");
        String[] time = input.localTime().split(":");

        try {
            return new Time(
                    null,
                    Integer.parseInt(date[0]),
                    Integer.parseInt(date[1]),
                    Integer.parseInt(date[2]),
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1])
            );
        } catch (NumberFormatException e) {
            throw new ParseException("Error while parsing event date/time!", -1);
        }
    }
}
