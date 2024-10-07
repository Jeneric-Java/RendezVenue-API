package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.*;
import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ParserTest {

    @Autowired
    Parser parser;

    @Nested
    @DisplayName("Event Parser Tests")
    class testEventParser {

        @Test
        @DisplayName("Returns correct result when given properly formatted input")
        void testCorrectFormat() throws ParseException {
            TicketmasterEvent inputEvent = new TicketmasterEvent(
                    "Test",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("Test Arena", "M2 5PD")})
            );
            LocationSet inputLocation = LocationSet.MANCHESTER;

            Event expected = new Event(
                    null,
                    "Test",
                    "Test @ Test Arena",
                    "M2 5PD",
                    "example.com",
                    EventType.MISC,
                    LocationSet.MANCHESTER,
                    "10:00:00",
                    "2024-10-01",
                    null,
                    null);

            Event actual = parser.parseEvent(inputEvent, inputLocation);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given null input")
        void testNullInput() {
            TicketmasterEvent inputEvent = null;
            LocationSet inputLocation = null;

            assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(inputEvent, inputLocation));
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given input with null name, url, or date")
        void testNullFieldsInput() {
            TicketmasterEvent input1 = new TicketmasterEvent(
                    null,
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{}));
            TicketmasterEvent input2 = new TicketmasterEvent(
                    "Test",
                    null,
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{}));
            TicketmasterEvent input3 = new TicketmasterEvent(
                    "Test",
                    "example.com",
                    null,
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{}));

            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input1, null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input2, null)),
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input3, null))
            );
        }

        @Test
        @DisplayName("Doesn't throw exception when given input with dates (but not times), classifications, or _embedded as null")
        void testNullFieldsInputWhenShouldStillPass() {
            TicketmasterEvent input = new TicketmasterEvent("Test", "Test", new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date(null, "2024-10-01")), null, null);

            assertDoesNotThrow(() -> parser.parseEvent(input, null));
        }

        @Test
        @DisplayName("Uses sensible fallbacks when given input with null fields in nested records")
        void testNullInternalFieldsInput() throws ParseException {
            LocationSet inputLocation = LocationSet.MANCHESTER;

            TicketmasterEvent input1 = new TicketmasterEvent(
                    "Test",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date(null, "2024-10-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("Test Arena", "M2 5PD")})
            );

            TicketmasterEvent input2 = new TicketmasterEvent(
                    "Test",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(null)},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("Test Arena", "M2 5PD")})
            );

            TicketmasterEvent input3 = new TicketmasterEvent(
                    "Test",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{})
            );

            Event expected1 = new Event(
                    null,
                    "Test",
                    "Test @ Test Arena",
                    "M2 5PD",
                    "example.com",
                    EventType.MISC,
                    LocationSet.MANCHESTER,
                    null,
                    "2024-10-01",
                    null,
                    null);

            Event expected2 = new Event(
                    null,
                    "Test",
                    "Test @ Test Arena",
                    "M2 5PD",
                    "example.com",
                    EventType.MISC,
                    LocationSet.MANCHESTER,
                    "10:00:00",
                    "2024-10-01",
                    null,
                    null);

            Event expected3 = new Event(
                    null,
                    "Test",
                    "Event (" + EventType.MISC + "): Test",
                    "No Location Given",
                    "example.com",
                    EventType.MISC,
                    LocationSet.MANCHESTER,
                    "10:00:00",
                    "2024-10-01",
                    null,
                    null);

            Event actual1 = parser.parseEvent(input1, inputLocation);
            Event actual2 = parser.parseEvent(input2, inputLocation);
            Event actual3 = parser.parseEvent(input3, inputLocation);

            assertAll(
                    () -> assertEquals(expected1, actual1),
                    () -> assertEquals(expected2, actual2),
                    () -> assertEquals(expected3, actual3)
            );
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given input with invalid date")
        void testBadDateInput() {
            TicketmasterEvent input = new TicketmasterEvent(
                    "Test",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("Ten O'Clock", "The Tenth of October")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("M2 5PD", "Test Arena")})
            );

            assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input, null));
        }
    }

    @Nested
    @DisplayName("Page Parser Tests")
    class testPageParser {

        @Test
        @DisplayName("Returns correct result when given valid array of events")
        void testValidInput() throws ParseException {
            LocationSet inputLocation = LocationSet.BELFAST;

            TicketmasterEvent ticketmasterEvent1 = new TicketmasterEvent(
                    "Event 1",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("Test Arena", "M2 5PD")}));
            TicketmasterEvent ticketmasterEvent2 = new TicketmasterEvent(
                    "Event 2",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("12:30:00", "2024-10-02")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("Test Arena", "M2 5PD")}));

            TicketmasterPage input = new TicketmasterPage(
                    new TicketmasterPage.Embedded(new TicketmasterEvent[]{ticketmasterEvent1, ticketmasterEvent2}),
                    new TicketmasterPage.Page(2, 2, 1, 0)
            );

            Event event1 = new Event(null, "Event 1", "Event 1 @ Test Arena", "M2 5PD", "example.com", EventType.MISC, LocationSet.BELFAST, "10:00:00", "2024-10-01", null, null);
            Event event2 = new Event(null, "Event 2", "Event 2 @ Test Arena", "M2 5PD", "example.com", EventType.MISC, LocationSet.BELFAST, "12:30:00", "2024-10-02", null, null);

            List<Event> expected = List.of(event1, event2);

            List<Event> actual = parser.parsePage(input, inputLocation);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given null page")
        void testNullPage() {
            TicketmasterPage input = null;

            assertThrows(IllegalArgumentException.class, () -> parser.parsePage(input, null));
        }

        @Test
        @DisplayName("Throws NoResultsFoundException when given page with null _embedded, or _embedded with a null/empty array")
        void testNoResultsFound() {
            TicketmasterPage.Page page = new TicketmasterPage.Page(1, 0, 0,0);

            TicketmasterPage input1 = new TicketmasterPage(null, page);
            TicketmasterPage input2 = new TicketmasterPage(new TicketmasterPage.Embedded(null), page);
            TicketmasterPage input3 = new TicketmasterPage(new TicketmasterPage.Embedded(new TicketmasterEvent[0]), page);

            assertAll(
                    () -> assertThrows(NoResultsFoundException.class, () -> parser.parsePage(input1, null)),
                    () -> assertThrows(NoResultsFoundException.class, () -> parser.parsePage(input2, null)),
                    () -> assertThrows(NoResultsFoundException.class, () -> parser.parsePage(input3, null))
            );
        }

        @Test
        @DisplayName("Returns correct list when given page with correct _embedded but no page info")
        void testNoPageInfo() throws ParseException {
            LocationSet inputLocation = LocationSet.GLASGOW;

            TicketmasterEvent ticketmasterEvent1 = new TicketmasterEvent(
                    "Event 3",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("10:00:00", "2025-02-01")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("Test Arena", "M2 5PD")}));
            TicketmasterEvent ticketmasterEvent2 = new TicketmasterEvent(
                    "Event 4",
                    "example.com",
                    new TicketmasterEvent.Dates(new TicketmasterEvent.Dates.Date("12:30:00", "2025-02-02")),
                    new TicketmasterEvent.Classifications[]{new TicketmasterEvent.Classifications(new TicketmasterEvent.Classifications.Segment("KZFzniwnSyZfZ7v7n1"))},
                    new TicketmasterEvent.Embedded(new TicketmasterEvent.Embedded.Venue[]{new TicketmasterEvent.Embedded.Venue("Test Arena", "M2 5PD")}));

            TicketmasterPage input = new TicketmasterPage(
                    new TicketmasterPage.Embedded(new TicketmasterEvent[]{ticketmasterEvent1, ticketmasterEvent2}),
                    null
            );

            Event event1 = new Event(null, "Event 3", "Event 3 @ Test Arena", "M2 5PD", "example.com", EventType.MISC, LocationSet.GLASGOW, "10:00:00", "2025-02-01", null, null);
            Event event2 = new Event(null, "Event 4", "Event 4 @ Test Arena", "M2 5PD", "example.com", EventType.MISC, LocationSet.GLASGOW, "12:30:00", "2025-02-02", null, null);

            List<Event> expected = List.of(event1, event2);

            List<Event> actual = parser.parsePage(input, inputLocation);

            assertEquals(expected, actual);
        }
    }
}