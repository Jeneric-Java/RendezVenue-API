//package com.Jeneric_Java.calendarappapi.repository;
//
//import com.Jeneric_Java.calendarappapi.model.Event;
//import com.Jeneric_Java.calendarappapi.model.EventType;
//import com.Jeneric_Java.calendarappapi.model.Time;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@DataJpaTest
//class EventRepositoryTest {
//
//    @Autowired
//    EventRepository apiRepository;
//
//    @Test
//    @DisplayName("Writes to, and reads from, H2 in-memory database three unconstrained instances of class Event")
//    public void testGetAllEvents() {
//
//
//    }
//
//}
//
////    @Test
////    @DisplayName("Writes to, and reads from, H2 in-memory database three unconstrained instances of class Event")
////    public void testGetAllEvents() {
////
////        // Arrange
////        List<Event> expectedEventList = getEventList();
////
////        // Act
////        apiRepository.saveAll(expectedEventList);
////        Iterable<Event> actualEventList = apiRepository.findAll();
////
////        // Assert
////        assertThat(actualEventList).hasSize(3);
////        assertThat(expectedEventList).isEqualTo(actualEventList);
////    }
////
//////    @Test
//////    @DisplayName("Writes to, and reads from, H2 in-memory database three instances of class Event constrained by three nested instances of class Time.")
//////    public void testGetAllEventsWithTime() {
//////
//////        // Arrange
//////        List<Event> expectedEventList = getEventListWithTime();
////////        expectedEventList.forEach(System.out::println);
//////
//////        // Act
//////        apiRepository.saveAll(expectedEventList);
//////        Iterable<Event> actualEventList = apiRepository.findAll();
//////
//////        // Assert
//////        assertThat(actualEventList).hasSize(3);
//////        assertThat(expectedEventList).isEqualTo(actualEventList);
//////    }
////
//////    private static List<Event> getEventListWithTime() {
//////        Event event1 = new Event(1L, "title1",
//////                "description1", "location1", "url1",
//////                EventType.MISC, "city1",
//////                Set.of(new Time(1L, 2018, 10, 4, 15, 30, true, null),
//////                        new Time(2L, 2018, 10, 4, 16, 30, false, null)));
//////        Event event2 = new Event(2L, "title2",
//////                "description2", "location2",
//////                "url2", EventType.MISC, "city2",
//////                Set.of(new Time(3L, 2020, 10, 3, 15, 30, true, null),
//////                        new Time(4L, 2020, 10, 4, 15, 30, false, null)));
//////        Event event3 = new Event(3L, "title3",
//////                "description3", "location3",
//////                "url3", EventType.MISC, "city3",
//////                Set.of(new Time(5L, 2024, 3, 19, 15, 30, true, null),
//////                        new Time(6L, 2024, 3, 19, 17, 45, false, null)));
//////
//////        List<Event> expectedEventList = new ArrayList<>();
//////        expectedEventList.add(event1);
//////        expectedEventList.add(event2);
//////        expectedEventList.add(event3);
//////        return expectedEventList;
//////    }
////
////    private static List<Event> getEventList() {
////        Event event1 = new Event(1L, "title1", "description1", "location1", "url1", EventType.MISC, "city1", null);
////        Event event2 = new Event(2L, "title2", "description2", "location2", "url2", EventType.MISC, "city2", null);
////        Event event3 = new Event(3L, "title3", "description3", "location3", "url3", EventType.MISC, "city3", null);
////
////        List<Event> expectedEventList = new ArrayList<>();
////        expectedEventList.add(event1);
////        expectedEventList.add(event2);
////        expectedEventList.add(event3);
////        return expectedEventList;
////    }
////}