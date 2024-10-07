package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.service.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "RendezVenue API", description = "Find events across the UK and Ireland")
public class ApiController {

    @Autowired
    private ApiService apiService;

    // GET all events by location
    @Operation(summary = "Get a list of events from results in a given area")
    @GetMapping()
    public ResponseEntity<List<Event>> getEventsByLocation(@RequestParam @Parameter(name = "geoHashEnc", description = "Encoded geoHash of the area to be searched", required = true) String geoHashEnc) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, IOException, InvalidKeyException {
        return new ResponseEntity<>(apiService.getEventsByLocation(geoHashEnc), HttpStatus.OK);
    }

    // GET event by id
    @Operation(summary = "Get info on a specific event in our database by id")
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable @Parameter(name = "id", description = "Id of the event to be retrieved") Long id){
        return new ResponseEntity<>(apiService.getEventByID(id), HttpStatus.OK);
    }

    // DELETE event by id
    @Operation(summary = "Delete a specific event in our database by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEventById(@PathVariable @Parameter(name = "id", description = "Id of the event to be deleted") Long id){
        return new ResponseEntity<>(apiService.deleteEventById(id), HttpStatus.OK);
    }

    // POST event
    @Operation(summary = "Create a new event in our database")
    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Event to be added to the database") Event event) {
        Event newEvent  = apiService.insertEvent(event);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("event", "/api/events/" + newEvent.getId().toString());
        return new ResponseEntity<>(newEvent, httpHeaders, HttpStatus.CREATED);
    }

    // UPDATE event
    @Operation(summary = "Update the info on an event in our database by id")
    @PatchMapping("/{id}")
    public ResponseEntity<Event> updateEventById(@PathVariable("id") @Parameter(name = "id", description = "Id of the event to be updated") Long id,
                                                 @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated event to be put in the database") Event event) {
        return new ResponseEntity<>(apiService.updateEventById(id, event), HttpStatus.NO_CONTENT);
    }
}