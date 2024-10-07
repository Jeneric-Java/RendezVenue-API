package com.Jeneric_Java.calendarappapi.repository;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.EventType;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("Writes to, and reads from, H2 in-memory database three unconstrained instances of class Event")
    public void testGetAllEvents() {

        // Arrange
        List<Event> expectedEventList = getEventList();

        // Act
        eventRepository.saveAll(expectedEventList);
        Iterable<Event> actualEventList = eventRepository.findAll();

        // Assert
        assertThat(actualEventList).hasSize(3);
        assertThat(expectedEventList).isEqualTo(actualEventList);
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



