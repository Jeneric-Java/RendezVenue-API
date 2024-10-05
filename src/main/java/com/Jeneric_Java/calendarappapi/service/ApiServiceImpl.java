package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.Locations;
import com.Jeneric_Java.calendarappapi.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class ApiServiceImpl implements ApiService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TicketmasterService ticketmasterService;

    @Override
    public List<Event> getEventsByLocation(Locations location) {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        try {
            events.addAll(ticketmasterService.getEventFromCache(location));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public Event getEventByID(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()) {
            return event.get();
        } else {
            throw new NoResultsFoundException(String.format("Event with id '%s' cannot be located.", id));
        }
    }

    @Override
    public String deleteEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if(event.isPresent()) {
            eventRepository.deleteById(id);
            return String.format("Event with id '%s' has been deleted successfully.", id);
        } else {
            throw new NoResultsFoundException(String.format("An event with id '%s' cannot be located.", id));
        }
    }
    @Override
    public Event insertEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEventById(Long id, Event event) {
        Optional<Event> eventFromRepo = eventRepository.findById(id);
        Event newEvent;
        if (eventFromRepo.isPresent()) {
            newEvent = eventFromRepo.get();
            newEvent.setTitle(event.getTitle());
            newEvent.setDescription(event.getDescription());
            newEvent.setLocation(event.getLocation());
            newEvent.setClosestCity(event.getClosestCity());
            newEvent.setUrl(event.getUrl());
            newEvent.setType(event.getType());
            return eventRepository.save(newEvent);
        } else {
            throw new NoResultsFoundException(String.format("An event with id '%s' cannot be located.", id));
        }
    }
}
