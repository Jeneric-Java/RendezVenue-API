package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.model.Event;

public interface ApiService {

    Event getEventByID (String id);
    Event deleteEventById(String id);
    Event getAllEvents(String location, String title);
}
