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

@DataJpaTest
public class IntegratedRepositoryTest {

    @Autowired
    TimeRepository timeRepository;

    @Autowired
    EventRepository eventRepository;

    @Test
    @DisplayName("Writes to, and reads from, H2 in-memory database three unconstrained instances of class Event")
    public void testGetAllEvents() {

        // Arrange
        List<Event> unconstrainedEventList = getEventList();
        List<Time> expectedTimeList = new ArrayList<>();

        Time time1 = new Time(1L, 2018, 10, 4, 15, 30, true, unconstrainedEventList.get(0));
        Time time2 = new Time(2L, 2018, 10, 4, 16, 30, false, unconstrainedEventList.get(1));
        Time time3 = new Time(3L, 2020, 10, 3, 15, 30, true, unconstrainedEventList.get(2));

//        unconstrainedEventList.get(0).addTime(time1);
//        unconstrainedEventList.get(1).addTime(time2);
//        unconstrainedEventList.get(2).addTime(time3);

        expectedTimeList.add(time1);
        expectedTimeList.add(time2);
        expectedTimeList.add(time3);

        // Act

//        timeRepository.saveAll(expectedTimeList);
        eventRepository.saveAll(unconstrainedEventList);

//        Iterable<Time> actualTimeList = timeRepository.findAll();
        Iterable<Event> actualEventList = eventRepository.findAll();

        // Assert
        assertThat(actualEventList).hasSize(3);
        assertThat(unconstrainedEventList).isEqualTo(actualEventList);
//
//        assertThat(actualTimeList).hasSize(3);
//        assertThat(expectedTimeList).isEqualTo(actualTimeList);
    }

    private static List<Event> getEventList() {
        Event event1 = new Event(1L, "title1", "description1", "location1", "url1", EventType.MISC, "city1", new HashSet<>());
        Event event2 = new Event(2L, "title2", "description2", "location2", "url2", EventType.MISC, "city2", new HashSet<>());
        Event event3 = new Event(3L, "title3", "description3", "location3", "url3", EventType.MISC, "city3", new HashSet<>());

        Time time1 = new Time(1L, 2018, 10, 4, 15, 30, true);
        Time time2 = new Time(2L, 2018, 10, 4, 16, 30, false);
        Time time3 = new Time(3L, 2020, 10, 3, 15, 30, true);


        event1.addTime(time1, time2);
        event2.addTime(time2, time3);
        event3.addTime(time1, time3);


        List<Event> expectedEventList = new ArrayList<>();
        expectedEventList.add(event1);
        expectedEventList.add(event2);
        expectedEventList.add(event3);
        return expectedEventList;
    }
}
