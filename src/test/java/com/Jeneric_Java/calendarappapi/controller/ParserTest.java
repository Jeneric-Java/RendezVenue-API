package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.ApiEvent;
import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.Time;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;

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

}