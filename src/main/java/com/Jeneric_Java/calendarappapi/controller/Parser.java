package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.*;
import com.google.common.collect.ImmutableMap;
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
                parseSegment(input.classifications()[0].segment()),
        //        parseTime(input.dates().start()),
                null,
                null
        );
    }

    private EventType parseSegment(ApiEvent.Classifications.Segment segment) {
        final ImmutableMap<String, EventType> eventTypes = ImmutableMap.of(
                "KZFzniwnSyZfZ7v7na", EventType.ART_THEATRE,
                "KZFzniwnSyZfZ7v7n1", EventType.MISC,
                "KZFzniwnSyZfZ7v7nE", EventType.SPORT,
                "KZFzniwnSyZfZ7v7nJ", EventType.MUSIC,
                "KZFzniwnSyZfZ7v7nn", EventType.FILM
        );
        if (segment.id() == null) return EventType.MISC;
        return eventTypes.getOrDefault(segment.id(), EventType.MISC);
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
                    Integer.parseInt(time[1]),
                    null
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
