package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.exception.NoResultsFoundException;
import com.Jeneric_Java.calendarappapi.model.ApiEvent;
import com.Jeneric_Java.calendarappapi.model.ApiPage;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.Time;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.ArrayList;
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
            ApiEvent input = new ApiEvent(
                    "Test",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")})
            );
            Event expected = new Event(
                    null,
                    "Test",
                    "Test @ Test Arena",
                    "M2 5PD", "example.com",
                    "Test",
                    new Time(null, 2024, 10, 1, 10, 0),
                    null);

            Event actual = parser.parseEvent(input);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given null input")
        void testNullInput() {
            ApiEvent input = null;

            assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input));
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given input with null fields")
        void testNullFieldsInput() {
            ApiEvent input = new ApiEvent(null, null, null, null, null);

            assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input));
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given input with null fields in nested records")
        void testNullInternalFieldsInput() {
            ApiEvent input1 = new ApiEvent(
                    "Test",
                    "example.com",
                    new ApiEvent.Dates(null),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")})
            );

            ApiEvent input2 = new ApiEvent(
                    "Test",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(null)},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")})
            );

            ApiEvent input3 = new ApiEvent(
                    "Test",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{})
            );

            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input1)),
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input2)),
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseEvent(input3))
            );
        }

        @Test
        @DisplayName("Throws ParseException when given input with invalid date")
        void testBadDateInput() {
            ApiEvent input = new ApiEvent(
                    "Test",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("Ten O'Clock", "The Tenth of October")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("M2 5PD", "Test Arena")})
            );

            assertThrows(ParseException.class, () -> parser.parseEvent(input));
        }
    }

    @Nested
    @DisplayName("Time Parser Tests")
    class testTimeParser {

        @Test
        @DisplayName("Returns correct result when given properly formatted input")
        void testCorrectFormat() throws ParseException {
            ApiEvent.Dates.Date input = new ApiEvent.Dates.Date("10:00:00", "2024-10-01");
            Time expected = new Time(null, 2024, 10, 1, 10, 0);

            Time actual = parser.parseTime(input);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given null input")
        void testNullInput() {
            ApiEvent.Dates.Date input = null;

            assertThrows(IllegalArgumentException.class, () -> parser.parseTime(input));
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given input with null strings")
        void testNullStringInput() {
            ApiEvent.Dates.Date input1 = new ApiEvent.Dates.Date(null, "2024-10-01");
            ApiEvent.Dates.Date input2 = new ApiEvent.Dates.Date("10:00:00", null);

            assertAll(
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseTime(input1)),
                    () -> assertThrows(IllegalArgumentException.class, () -> parser.parseTime(input2))
            );
        }

        @Test
        @DisplayName("Throws ParseException when given input with invalid strings")
        void testBadStringInput() {
            ApiEvent.Dates.Date input1 = new ApiEvent.Dates.Date("Ten O'Clock", "2024-10-01");
            ApiEvent.Dates.Date input2 = new ApiEvent.Dates.Date("10:00:00", "The Tenth of October");

            assertAll(
                    () -> assertThrows(ParseException.class, () -> parser.parseTime(input1)),
                    () -> assertThrows(ParseException.class, () -> parser.parseTime(input2))
            );
        }
    }

    @Nested
    @DisplayName("Page Parser Tests")
    class testPageParser {

        @Test
        @DisplayName("Returns correct result when given valid array of events")
        void testValidInput() throws ParseException {
            ApiEvent apiEvent1 = new ApiEvent(
                    "Event 1",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2024-10-01")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")}));
            ApiEvent apiEvent2 = new ApiEvent(
                    "Event 2",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("12:30:00", "2024-10-02")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")}));

            ApiPage input = new ApiPage(
                    new ApiPage.Embedded(new ApiEvent[]{apiEvent1, apiEvent2}),
                    new ApiPage.Page(2, 2, 1, 0)
            );

            Event event1 = new Event(null, "Event 1", "Event 1 @ Test Arena", "M2 5PD", "example.com", "Test", new Time(null, 2024, 10, 1, 10, 0), null);
            Event event2 = new Event(null, "Event 2", "Event 2 @ Test Arena", "M2 5PD", "example.com", "Test", new Time(null, 2024, 10, 2, 12, 30), null);

            List<Event> expected = List.of(event1, event2);

            List<Event> actual = parser.parsePage(input);

            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Throws IllegalArgumentException when given null page")
        void testNullPage() {
            ApiPage input = null;

            assertThrows(IllegalArgumentException.class, () -> parser.parsePage(input));
        }

        @Test
        @DisplayName("Throws NoResultsFoundException when given page with null _embedded, or _embedded with a null/empty array")
        void testNoResultsFound() {
            ApiPage.Page page = new ApiPage.Page(1, 0, 0,0);

            ApiPage input1 = new ApiPage(null, page);
            ApiPage input2 = new ApiPage(new ApiPage.Embedded(null), page);
            ApiPage input3 = new ApiPage(new ApiPage.Embedded(new ApiEvent[0]), page);

            assertAll(
                    () -> assertThrows(NoResultsFoundException.class, () -> parser.parsePage(input1)),
                    () -> assertThrows(NoResultsFoundException.class, () -> parser.parsePage(input2)),
                    () -> assertThrows(NoResultsFoundException.class, () -> parser.parsePage(input3))
            );
        }

        @Test
        @DisplayName("Returns correct list when given page with correct _embedded but no page info")
        void testNoPageInfo() throws ParseException {
            ApiEvent apiEvent1 = new ApiEvent(
                    "Event 3",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2025-02-01")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")}));
            ApiEvent apiEvent2 = new ApiEvent(
                    "Event 4",
                    "example.com",
                    new ApiEvent.Dates(new ApiEvent.Dates.Date("12:30:00", "2025-02-02")),
                    new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
                    new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")}));

            ApiPage input = new ApiPage(
                    new ApiPage.Embedded(new ApiEvent[]{apiEvent1, apiEvent2}),
                    null
            );

            Event event1 = new Event(null, "Event 3", "Event 3 @ Test Arena", "M2 5PD", "example.com", "Test", new Time(null, 2025, 2, 1, 10, 0), null);
            Event event2 = new Event(null, "Event 4", "Event 4 @ Test Arena", "M2 5PD", "example.com", "Test", new Time(null, 2025, 2, 2, 12, 30), null);

            List<Event> expected = List.of(event1, event2);

            List<Event> actual = parser.parsePage(input);

            assertEquals(expected, actual);
        }
    }
}