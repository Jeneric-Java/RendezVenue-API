package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.EventType;
import com.Jeneric_Java.calendarappapi.model.Time;
import com.Jeneric_Java.calendarappapi.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ApiService apiService;

    @Test
    @DisplayName("get all events")
    public void getAllEvents() throws Exception {
        List<Event> mockEvents = Arrays.asList(
                new Event(1L, "title1", "description1", "location1", "url1", EventType.MISC, new Time(1L, 2024, 9, 3, 12, 5, null), null, null),
                new Event(2L, "title2", "description2", "location2", "url2", EventType.MISC, new Time(2L, 2025, 4, 5, 7, 30, null), null, null),
                new Event(3L, "title3", "description3", "location3", "url3", EventType.MISC, new Time(3L, 2025, 12, 25, 0, 0, null), null, null)
        );

        when(apiService.getAllEvents("location1")).thenReturn(mockEvents);

        var test = mockMvc.perform(get("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .param("location", "location1"));

        mockMvc.perform(get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("location", "location1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andExpect(jsonPath("$[0].description").value("description1"))
                .andExpect(jsonPath("$[0].location").value("location1"))
                .andExpect(jsonPath("$[0].url").value("url1"))
                .andExpect(jsonPath("$[1].title").value("title2"))
                .andExpect(jsonPath("$[1].description").value("description2"))
                .andExpect(jsonPath("$[1].location").value("location2"))
                .andExpect(jsonPath("$[1].url").value("url2"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].title").value("title3"))
                .andExpect(jsonPath("$[2].description").value("description3"))
                .andExpect(jsonPath("$[2].location").value("location3"))
                .andExpect(jsonPath("$[2].url").value("url3"))
    }
}