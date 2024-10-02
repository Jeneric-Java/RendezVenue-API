package com.Jeneric_Java.calendarappapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Time {
    Long id;
    int year;
    int month;
    int day;
    int hour;
    int minute;
}
