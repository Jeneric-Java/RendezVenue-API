package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    ApiRepository apiRepository;



    @Override
    public List<Event> getAllEvents(String location) {
        List<Event> events = new ArrayList<>();
        apiRepository.findAll().forEach(events::add);
        return events;
    }

    @Override
    public Event getEventByID(Long id) {
        Optional<Event> event = apiRepository.findById(id);
        if(event.isPresent()) {
            return event.get();
        } else {
            throw new NoResultsFoundException(String.format("Event with id '%s' cannot be located.", id));
        }
    }

    @Override
    public String deleteEventById(Long id) {
        Optional<Event> event = apiRepository.findById(id);
        if(event.isPresent()) {
            apiRepository.deleteById(id);
            return String.format("Event with id '%s' has been deleted successfully.", id);
        } else {
            throw new NoResultsFoundException(String.format("An event with id '%s' cannot be located.", id));
        }
    }

    @Override
    public Event insertEvent(Event event) {
        return apiRepository.save(event);
    }
}
