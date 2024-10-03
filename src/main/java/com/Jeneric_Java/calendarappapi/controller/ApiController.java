package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/events")
    public class ApiController {

        @Autowired
        private ApiService apiService;

        // GET all events
        @GetMapping()
       public ResponseEntity<List<Event>> getAllEvents(@RequestParam String location){
            return new ResponseEntity<>(apiService.getAllEvents(location), HttpStatus.OK);
        }

        // GET event by id
        @GetMapping("/{id}")
        public Event getEventById(@PathVariable Long id){
            return apiService.getEventByID(id);
        }

        @DeleteMapping("/{id}")
        public String deleteEventById(@PathVariable Long id){
            apiService.deleteEventById(id);
        }

    }

