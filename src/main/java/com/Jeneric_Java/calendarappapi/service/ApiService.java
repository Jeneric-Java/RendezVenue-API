package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.model.Event;

import java.util.List;

public interface ApiService {

        List<Event> getAllEvents(String location);
        Event getEventByID (Long id);
        Event deleteEventById(Long id);
}
