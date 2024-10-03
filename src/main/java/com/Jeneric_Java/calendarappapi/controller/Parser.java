package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.*;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Parser {

    public Event parseEvent(ApiEvent input) throws ParseException {
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

        return new Event(
                null,
                name,
                description,
                postalCode,
                url,
                type,
                parseTime(input.dates().start()),
                null,
                null
        );
    }

    private EventType parseSegment(ApiEvent.Classifications.Segment segment) {
        if (segment.id() == null) return EventType.MISC;

        return switch (segment.id()) {
            case "KZFzniwnSyZfZ7v7na" -> EventType.ART_THEATRE;
            case "KZFzniwnSyZfZ7v7nE" -> EventType.SPORT;
            case "KZFzniwnSyZfZ7v7nJ" -> EventType.MUSIC;
            case "KZFzniwnSyZfZ7v7nn" -> EventType.FILM;
            default -> EventType.MISC;
        };
    }

    public Time parseTime(ApiEvent.Dates.Date input) throws ParseException {
        if (input == null) throw new IllegalArgumentException("Error while parsing event date/time! Date/Time cannot be null!");

        Integer year, month, day, hour, minute;

        if (input.localDate() == null || input.localDate().isBlank()) {
            throw new IllegalArgumentException("Date cannot be null!");
        } else {
            String[] date = input.localDate().split("-");
            try {
                year = Integer.parseInt(date[0]);
                month = Integer.parseInt(date[1]);
                day = Integer.parseInt(date[2]);
            } catch (NumberFormatException e) {
                throw new ParseException("Error while parsing event date!", -1);
            }
        }

        if (input.localTime() == null || input.localTime().isBlank()) {
            hour = minute = null;
        } else {
            String[] time = input.localTime().split(":");
            try {
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
            } catch (NumberFormatException e) {
                throw new ParseException("Error while parsing event time!", -1);
            }
        }

        return new Time(null, year, month, day, hour, minute, null);
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
