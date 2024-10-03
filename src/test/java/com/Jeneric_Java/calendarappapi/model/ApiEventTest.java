//package com.Jeneric_Java.calendarappapi.model;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ApiEventTest {
//
//    @Test
//    @DisplayName("ApiEvent.hasNullFields returns correct results")
//    void eventHasNullFields() {
//        ApiEvent eventValid = new ApiEvent(
//                "Test",
//                "example.com",
//                new ApiEvent.Dates(new ApiEvent.Dates.Date("10:00:00", "2024-10-01")),
//                new ApiEvent.Classifications[]{new ApiEvent.Classifications(new ApiEvent.Classifications.Segment("Test"))},
//                new ApiEvent.Embedded(new ApiEvent.Embedded.Venue[]{new ApiEvent.Embedded.Venue("Test Arena", "M2 5PD")})
//        );
//        ApiEvent eventNullFields = new ApiEvent(null, null, null, null, null);
//
//        assertAll(
//                () -> assertFalse(eventValid.hasNullFields()),
//                () -> assertTrue(eventNullFields.hasNullFields())
//        );
//    }
//
//    @Test
//    @DisplayName("ApiEvent.Dates.Date.hasNullFields returns correct results")
//    void dateHasNullFields() {
//        ApiEvent.Dates.Date dateValid = new ApiEvent.Dates.Date("12:00:00", "2024-10-01");
//        ApiEvent.Dates.Date dateNullFields = new ApiEvent.Dates.Date(null, null);
//
//        assertAll(
//                () -> assertFalse(dateValid.hasNullFields()),
//                () -> assertTrue(dateNullFields.hasNullFields())
//        );
//    }
//}