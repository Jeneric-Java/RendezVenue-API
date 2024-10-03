package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.ApiEvent;
import com.Jeneric_Java.calendarappapi.model.ApiPage;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.Time;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Parser {

    public Event parseEvent(ApiEvent input) throws ParseException {
        if (input == null || input.hasNullFields()) throw new IllegalArgumentException("Event cannot be null!");

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
        if (input == null || input.hasNullFields()) throw new IllegalArgumentException("Error while parsing event date/time! Date/Time cannot be null!");

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

    public List<Event> parsePage(ApiPage input) throws ParseException {
        if (input == null) throw new IllegalArgumentException("Page cannot be null!");
        if (input._embedded() == null || input._embedded().events() == null || input._embedded().events().length == 0) throw new NoResultsFoundException("No results in given page!");

        ArrayList<Event> events = new ArrayList<>();

        for (ApiEvent event : input._embedded().events()) {
            events.add(parseEvent(event));
        }

        return events;
    }
}
