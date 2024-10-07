package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.repository.EventRepository;
import com.Jeneric_Java.calendarappapi.service.location.locationparser.LocationParser;
import com.Jeneric_Java.calendarappapi.service.location.utilities.Location;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    public List<Event> getEventsByLocation(String geoHashEnc) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, IOException, InvalidKeyException {
        List<Event> events = new ArrayList<>();

        List<Location> locations = LocationParser.getCoverageByUserLocation(geoHashEnc);

        List<LocationSet> nodes = new ArrayList<>();

        locations.forEach(location -> nodes.add(location.getLocationSet()));

        eventRepository.findAll().forEach(events::add);

        List<Event> filteredEvents = new ArrayList<>(events.stream().filter(event -> nodes.contains(event.getClosestCity())).toList());

        try {
            for (LocationSet node : nodes) {
                filteredEvents.addAll(ticketmasterService.getEventFromCache(node));
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return filteredEvents;
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
