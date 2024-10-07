package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.*;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Parser {

    public Event parseEvent(TicketmasterEvent input, LocationSet location) throws ParseException {
        if (input == null
                || input.name() == null
                || input.url() == null
                || input.dates() == null
                || input.dates().start() == null) {
            throw new IllegalArgumentException("Event cannot be null!");
        }

        String name = input.name();

        String postalCode;
        if (input._embedded() == null
                || input._embedded().venues() == null
                || input._embedded().venues().length == 0) {
            postalCode = "No Location Given";
        } else {
            postalCode = input._embedded().venues()[0].postalCode() != null
                    ? input._embedded().venues()[0].postalCode()
                    : "No Location Given";
        }

        String url = input.url();

        EventType type =
                (input.classifications() != null && input.classifications()[0].segment() != null)
                        ? parseSegment(input.classifications()[0].segment())
                        : EventType.MISC;

        String description;
        if (input._embedded() == null
                || input._embedded().venues() == null
                || input._embedded().venues().length == 0
                || input._embedded().venues()[0].name() == null) {
            description = "Event (" + type + "): " + input.name();
        } else {
            description = input.name() + " @ " + input._embedded().venues()[0].name();
        }

        String startTime;
        if (input.dates().start().localTime() == null) {
            startTime = null;
        } else {
            if (input.dates().start().localTime().matches("^\\d\\d:\\d\\d:\\d\\d$")
                    || input.dates().start().localTime().matches("^\\d\\d:\\d\\d$")) {
                startTime = input.dates().start().localTime();
            } else {
                throw new IllegalArgumentException("Time present in illegal format!");
            }
        }

        String startDate;
        if (input.dates().start().localDate().matches("^\\d\\d\\d\\d-\\d\\d-\\d\\d$")) {
            startDate = input.dates().start().localDate();
        } else {
            throw new IllegalArgumentException("Date in illegal format!");
        }

        return new Event(
                null,
                name,
                description,
                postalCode,
                url,
                type,
                location,
                startTime,
                startDate,
                null,
                null,
                null
        );
    }

    private EventType parseSegment(TicketmasterEvent.Classifications.Segment segment) {
        if (segment.id() == null) return EventType.MISC;

        return switch (segment.id()) {
            case "KZFzniwnSyZfZ7v7na" -> EventType.ART_THEATRE;
            case "KZFzniwnSyZfZ7v7nE" -> EventType.SPORT;
            case "KZFzniwnSyZfZ7v7nJ" -> EventType.MUSIC;
            case "KZFzniwnSyZfZ7v7nn" -> EventType.FILM;
            default -> EventType.MISC;
        };
    }

    public List<Event> parsePage(TicketmasterPage input, LocationSet location) throws ParseException {
        if (input == null) throw new IllegalArgumentException("Page cannot be null!");
        if (input._embedded() == null || input._embedded().events() == null || input._embedded().events().length == 0) throw new NoResultsFoundException("No results in given page!");

        ArrayList<Event> events = new ArrayList<>();

        for (TicketmasterEvent event : input._embedded().events()) {
            events.add(parseEvent(event, location));
        }

        return events;
    }
}