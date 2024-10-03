package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService{
    @Override
    public List<Event> getAllEvents(String location) {
        return null;
    }

    @Override
    public Event getEventByID(String id) {
        return null;
    }

    @Override
    public Event deleteEventById(String id) {
        return null;
    }
}
