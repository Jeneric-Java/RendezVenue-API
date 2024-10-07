package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

    @RestController
    @RequestMapping("/api/events")
    public class ApiController {

        @Autowired
        private ApiService apiService;

        // GET all events by location
        @GetMapping()
        public ResponseEntity<List<Event>> getEventsByLocation(@RequestParam String geoHashEnc) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, IOException, InvalidKeyException {
            return new ResponseEntity<>(apiService.getEventsByLocation(geoHashEnc), HttpStatus.OK);
        }

        // GET event by id
        @GetMapping("/{id}")
        public ResponseEntity<Event> getEventById(@PathVariable Long id){
            return new ResponseEntity<>(apiService.getEventByID(id), HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteEventById(@PathVariable Long id){
            return new ResponseEntity<>(apiService.deleteEventById(id), HttpStatus.OK);
        }
        @PostMapping
        public ResponseEntity<Event> addEvent(@RequestBody Event event) {
            Event newEvent  = apiService.insertEvent(event);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("event", "/api/events/" + newEvent.getId().toString());
            return new ResponseEntity<>(newEvent, httpHeaders, HttpStatus.CREATED);
        }
        @PatchMapping("/{id}")
        public ResponseEntity<Event> updateEventById(@PathVariable("id") Long id, @RequestBody Event event) {
            return new ResponseEntity<>(apiService.updateEventById(id, event), HttpStatus.NO_CONTENT);
        }

    }

