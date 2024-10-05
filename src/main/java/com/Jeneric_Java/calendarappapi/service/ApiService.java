package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.Locations;

import java.util.List;

public interface ApiService {

        List<Event> getEventsByLocation(Locations location);
        Event getEventByID (Long id);
        String deleteEventById(Long id);
        Event insertEvent(Event event);
        Event updateEventById(Long id, Event event);
}
