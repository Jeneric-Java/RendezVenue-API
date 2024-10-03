package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.controller.Parser;
import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.*;
import com.Jeneric_Java.calendarappapi.secrets.Secrets;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(TicketmasterService.class)
class TicketmasterServiceTest {
    @Autowired
    TicketmasterService service;

    @Autowired
    MockRestServiceServer server;

    @Autowired
    ObjectMapper mapper;

    private final Secrets secrets = new Secrets();
    private final String BASE_URL = "https://app.ticketmaster.com/discovery/v2/events";

    @Test
    @DisplayName("Correct list returned when getEventByGeoHash given valid geoHash of correct length")
    void testGetEventByGeoHashAllOk() throws JsonProcessingException, ParseException {
        ApiEvent apiEvent = new ApiEvent(
                "Test",
                "example.com",
                new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2024-10-01")),
                new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")})
        );
        ApiPage mockServerResult = new ApiPage(
                new ApiPage.Embedded(new ApiEvent[]{apiEvent, apiEvent, apiEvent}),
                new ApiPage.Page(3, 3, 1, 0)
        );

        Event event = new Event(
                null,
                "Test",
                "Test @ Test Arena",
                "M2 5PD",
                "example.com",
                EventType.MISC,
                new Time(null, 2024, 10, 1, 10, 0, null),
                null,
                null);
        List<Event> expected = List.of(event, event, event);

        server.expect(requestTo(BASE_URL + ".json?radius=5&locale=en-gb&size=200&geoPoint=gcw2hzyup&apikey=" + secrets.getTicketmasterKey()))
                .andRespond(withSuccess(mapper.writeValueAsString(mockServerResult), MediaType.APPLICATION_JSON));

        List<Event> actual = service.getEventByGeoHash("gcw2hzyup");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throws NoResultsFoundException when api returns nothing or a page with no entries to getEventByGeoHash")
    void testGetEventByGeoHashNothingFound() throws JsonProcessingException {
        server.expect(requestTo(BASE_URL + ".json?radius=5&locale=en-gb&size=200&geoPoint=gcw2hzyup&apikey=" + secrets.getTicketmasterKey()))
                .andRespond(withSuccess(mapper.writeValueAsString(null), MediaType.APPLICATION_JSON));

        server.expect(requestTo(BASE_URL + ".json?radius=5&locale=en-gb&size=200&geoPoint=u10j4bsgj&apikey=" + secrets.getTicketmasterKey()))
                .andRespond(withNoContent());

        server.expect(requestTo(BASE_URL + ".json?radius=5&locale=en-gb&size=200&geoPoint=u16pu4dqm&apikey=" + secrets.getTicketmasterKey()))
                .andRespond(withNoContent());

        assertAll(
                () -> assertThrows(NoResultsFoundException.class, () -> service.getEventByGeoHash("gcw2hzyup")),
                () -> assertThrows(NoResultsFoundException.class, () -> service.getEventByGeoHash("u10j4bsgj")),
                () -> assertThrows(NoResultsFoundException.class, () -> service.getEventByGeoHash("u16pu4dqm"))
        );
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when getEventByGeoHash called with bad geoHash")
    void testGetEventByGeoHashBadHash() {
        assertThrows(IllegalArgumentException.class, () -> service.getEventByGeoHash("this isn't a valid hash"));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when getEventByGeoHash called with geoHash of 3 or fewer characters")
    void testGetEventByGeoHashSmallHash() {
        assertThrows(IllegalArgumentException.class, () -> service.getEventByGeoHash("no"));
    }

    @Test
    @DisplayName("Correct list returned when getEventByGeoHash given geoHash that's longer than 9 characters")
    void testGetEventByGeoHashLongHash() throws JsonProcessingException, ParseException {
        ApiEvent apiEvent = new ApiEvent(
                "Test",
                "example.com",
                new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2024-10-01")),
                new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")})
        );
        ApiPage mockServerResult = new ApiPage(
                new ApiPage.Embedded(new ApiEvent[]{apiEvent, apiEvent, apiEvent}),
                new ApiPage.Page(3, 3, 1, 0)
        );

        Event event = new Event(
                null,
                "Test",
                "Test @ Test Arena",
                "M2 5PD",
                "example.com",
                EventType.MISC,
                new Time(null, 2024, 10, 1, 10, 0, null),
                null,
                null);
        List<Event> expected = List.of(event, event, event);

        server.expect(requestTo(BASE_URL + ".json?radius=5&locale=en-gb&size=200&geoPoint=gcw2hzyup&apikey=" + secrets.getTicketmasterKey()))
                .andRespond(withSuccess(mapper.writeValueAsString(mockServerResult), MediaType.APPLICATION_JSON));

        List<Event> actual = service.getEventByGeoHash("gcw2hzyupThisIsExtraText");

        assertEquals(expected, actual);
    }

    @TestConfiguration
    static class TicketmasterServiceTestConfig{
        @Bean
        Parser parser(){
            return new Parser();
        }
    }
}