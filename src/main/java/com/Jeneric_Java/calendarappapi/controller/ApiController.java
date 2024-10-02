package com.Jeneric_Java.calendarappapi.controller;


import com.Jeneric_Java.calendarappapi.model.ApiEvent;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class ApiController {

    @Autowired
    private ApiService apiService;

    // GET all events
    @GetMapping("/events")
    public List<Event> getAllEvents(@RequestParam String location){
        return apiService.getAllEvents(location);
    }

    // GET event by id
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable String id){
        return apiService.getEventByID(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEventById(@PathVariable String id){
        apiService.deleteEventById(id);
    }

}
