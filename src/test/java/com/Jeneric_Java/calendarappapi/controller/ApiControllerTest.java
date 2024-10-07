package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.EventType;
import com.Jeneric_Java.calendarappapi.service.ApiServiceImpl;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class ApiControllerTest {

    @Mock
    private ApiServiceImpl mockApiServiceImpl;

    @InjectMocks
    private ApiController apiController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(apiController).build();
        mapper = new ObjectMapper();
    }

    @Test
    void testGetEventById() throws Exception {
        List<Event> eventList = getEventList();

        when(mockApiServiceImpl.getEventByID(1L)).thenReturn(eventList.get(0));
        when(mockApiServiceImpl.getEventByID(2L)).thenReturn(eventList.get(1));
        when(mockApiServiceImpl.getEventByID(3L)).thenReturn(eventList.get(2));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/events/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("M11 5DL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("url1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("MISC"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closestCity").value("MANCHESTER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("15:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2024-08-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("16:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2024-08-01"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/events/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("B3 7GX"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("url2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("ART_THEATRE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closestCity").value("BIRMINGHAM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("19:45:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2018-09-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("06:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2018-09-16"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/events/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("L1 9PJ"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("url3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("FILM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closestCity").value("LEEDS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("11:15:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-02-14"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("11:45:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-02-14"));
    }

    @Test
    void testDeleteEventById() throws Exception {

        List<Event> eventList = getEventList();

        mockApiServiceImpl.insertEvent(eventList.get(0));
        mockApiServiceImpl.insertEvent(eventList.get(1));
        mockApiServiceImpl.insertEvent(eventList.get(2));

        when(mockApiServiceImpl.deleteEventById(3L)).thenReturn("Event with id 3 has been deleted successfully.");

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.delete("/api/events/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Event with id 3 has been deleted successfully."));
    }

    @Test
    void testAddEvent() throws Exception {

        List<Event> eventList = getEventList();

        when(mockApiServiceImpl.insertEvent(eventList.get(0))).thenReturn(eventList.get(0));
        when(mockApiServiceImpl.insertEvent(eventList.get(1))).thenReturn(eventList.get(1));
        when(mockApiServiceImpl.insertEvent(eventList.get(2))).thenReturn(eventList.get(2));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(eventList.get(0))))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("M11 5DL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("url1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("MISC"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closestCity").value("MANCHESTER"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("15:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2024-08-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("16:30:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2024-08-01"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(eventList.get(1))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("B3 7GX"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("url2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("ART_THEATRE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closestCity").value("BIRMINGHAM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("19:45:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2018-09-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("06:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2018-09-16"));

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/events/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(eventList.get(2))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("L1 9PJ"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("url3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("FILM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closestCity").value("LEEDS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("11:15:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-02-14"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("11:45:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-02-14"));

        verify(mockApiServiceImpl, times(1)).insertEvent(eventList.get(0));
        verify(mockApiServiceImpl, times(1)).insertEvent(eventList.get(1));
        verify(mockApiServiceImpl, times(1)).insertEvent(eventList.get(2));
    }

    @Test
    void testUpdateEventById() throws Exception {

        List<Event> eventList = getEventList();

        Event updatedEvent = new Event(2L, "title2", "description2", "B3 7GX", "url2", EventType.ART_THEATRE, LocationSet.BIRMINGHAM, "21:45:00", "2018-09-15", "06:00:00", "2018-09-16");

        mockApiServiceImpl.insertEvent(eventList.get(0));
        mockApiServiceImpl.insertEvent(eventList.get(1));
        mockApiServiceImpl.insertEvent(eventList.get(2));


        when(mockApiServiceImpl.updateEventById(2L, updatedEvent)).thenReturn(updatedEvent);

        this.mockMvcController.perform(
                        MockMvcRequestBuilders.patch("/api/events/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updatedEvent)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("B3 7GX"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("url2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("ART_THEATRE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.closestCity").value("BIRMINGHAM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("21:45:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2018-09-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("06:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2018-09-16"));

        verify(mockApiServiceImpl, times(1)).updateEventById(2L, updatedEvent);
    }

    private static List<Event> getEventList() {
        Event event1 = new Event(1L, "title1", "description1", "M11 5DL", "url1", EventType.MISC, LocationSet.MANCHESTER, "15:30:00", "2024-08-01", "16:30:00", "2024-08-01");
        Event event2 = new Event(2L, "title2", "description2", "B3 7GX", "url2", EventType.ART_THEATRE, LocationSet.BIRMINGHAM, "19:45:00", "2018-09-15", "06:00:00", "2018-09-16");
        Event event3 = new Event(3L, "title3", "description3", "L1 9PJ", "url3", EventType.FILM, LocationSet.LEEDS, "11:15:00", "2020-02-14", "11:45:00", "2020-02-14");

        List<Event> expectedEventList = new ArrayList<>();
        expectedEventList.add(event1);
        expectedEventList.add(event2);
        expectedEventList.add(event3);
        return expectedEventList;
    }
}