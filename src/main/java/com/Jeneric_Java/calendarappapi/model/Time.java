package com.Jeneric_Java.calendarappapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Time {
    long id;
    int year;
    int month;
    int day;
    int hour;
    int minute;
}
