package com.Jeneric_Java.calendarappapi.service;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.EventType;
import com.Jeneric_Java.calendarappapi.repository.EventRepository;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class ApiServiceImplTest {

    @Mock
    private EventRepository mockEventRepository;

    @InjectMocks
    private ApiServiceImpl apiServiceImpl;

    @Test
    public void testGetEventById() throws Exception {

        List<Event> eventList = getEventList();

        when(mockEventRepository.findById(1L)).thenReturn(Optional.ofNullable(eventList.get(0)));
        when(mockEventRepository.findById(2L)).thenReturn(Optional.ofNullable(eventList.get(1)));
        when(mockEventRepository.findById(3L)).thenReturn(Optional.ofNullable(eventList.get(2)));

        assertThat(apiServiceImpl.getEventByID(1L)).isEqualTo(eventList.get(0));
        assertThat(apiServiceImpl.getEventByID(2L)).isEqualTo(eventList.get(1));
        assertThat(apiServiceImpl.getEventByID(3L)).isEqualTo(eventList.get(2));
    }

    @Test
    public void testInsertEvent() throws Exception {

        List<Event> eventList = getEventList();

        when(mockEventRepository.save(eventList.get(0))).thenReturn(eventList.get(0));
        when(mockEventRepository.save(eventList.get(1))).thenReturn(eventList.get(1));
        when(mockEventRepository.save(eventList.get(2))).thenReturn(eventList.get(2));

        assertThat(apiServiceImpl.insertEvent(eventList.get(0))).isEqualTo(eventList.get(0));
        assertThat(apiServiceImpl.insertEvent(eventList.get(1))).isEqualTo(eventList.get(1));
        assertThat(apiServiceImpl.insertEvent(eventList.get(2))).isEqualTo(eventList.get(2));
    }

    @Test
    public void testUpdateEventByID() throws Exception {

        List<Event> eventList = getEventList();

        mockEventRepository.save(eventList.get(0));
        mockEventRepository.save(eventList.get(1));
        mockEventRepository.save(eventList.get(2));

        Event updatedEvent = new Event(2L, "title2", "description2", "B3 7GX", "url2", EventType.ART_THEATRE, LocationSet.BIRMINGHAM, "21:45:00", "2018-09-15", "06:00:00", "2018-09-16");

        when(mockEventRepository.findById(2L)).thenReturn(Optional.of(eventList.get(1)));
        when(mockEventRepository.save(updatedEvent)).thenReturn(updatedEvent);

        assertThat(apiServiceImpl.updateEventById(2L, updatedEvent)).isEqualTo(updatedEvent);
    }

    @Test
    public void testDeleteEventByID() throws Exception {

        List<Event> eventList = getEventList();

        when(mockEventRepository.findById(1L)).thenReturn(Optional.ofNullable(eventList.getFirst()));
        when(mockEventRepository.findById(2L)).thenReturn(Optional.empty());

        assertThat(apiServiceImpl.deleteEventById(1L)).isEqualTo("Event with id '1' has been deleted successfully.");
        assertThrows(NoResultsFoundException.class, () -> apiServiceImpl.deleteEventById(2L), "Exception not thrown.");
    }

    private static List<Event> getEventList() {
        Event event1 = new Event(1L, "title1", "description1", "location1", "url1", EventType.MISC, LocationSet.BELFAST, "15:30", "2024-08-01", "16:30", "2024-08-01");
        Event event2 = new Event(2L, "title2", "description2", "location2", "url2", EventType.MISC, LocationSet.DUBLIN, "19:45", "2018-09-15", "06:00", "2018-09-15");
        Event event3 = new Event(3L, "title3", "description3", "location3", "url3", EventType.MISC, LocationSet.BRISTOL, "11:15", "2020-02-14", "11:45", "2020-02-14");

        List<Event> expectedEventList = new ArrayList<>();
        expectedEventList.add(event1);
        expectedEventList.add(event2);
        expectedEventList.add(event3);
        return expectedEventList;
    }

}