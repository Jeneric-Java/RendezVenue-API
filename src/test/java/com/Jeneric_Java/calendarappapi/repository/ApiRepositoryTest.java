package com.Jeneric_Java.calendarappapi.repository;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.EventType;
import com.Jeneric_Java.calendarappapi.model.Time;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ApiRepositoryTest {

    @Autowired
    ApiRepository apiRepository;


    @Test
    @DisplayName("Writes to, and reads from, H2 in-memory database three unconstrained instances of class Event")
    public void testGetAllEvents2() {

    }

    @Test
    @DisplayName("Writes to, and reads from, H2 in-memory database three unconstrained instances of class Event")
    public void testGetAllEvents() {

        // Arrange
        List<Event> expectedEventList = getEventList();

        // Act
        apiRepository.saveAll(expectedEventList);
        Iterable<Event> actualEventList = apiRepository.findAll();

        // Assert
        assertThat(actualEventList).hasSize(3);
        assertThat(expectedEventList).isEqualTo(actualEventList);
    }
    private static List<Event> getEventList() {
        Event event1 = new Event(1L, "title1", "description1", "location1", "url1", EventType.MISC, "city1", null);
        Event event2 = new Event(2L, "title2", "description2", "location2", "url2", EventType.MISC, "city2", null );
        Event event3 = new Event(3L, "title3", "description3", "location3", "url3", EventType.MISC, "city3", null);

        List<Event> expectedEventList = new ArrayList<>();
        expectedEventList.add(event1);
        expectedEventList.add(event2);
        expectedEventList.add(event3);
        return expectedEventList;

       // (List.of(new Time(1L, 2024, 9, 3, 12, 5, null));
    }

}