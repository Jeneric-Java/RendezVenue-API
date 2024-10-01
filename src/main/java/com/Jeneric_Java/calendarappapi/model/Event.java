package com.Jeneric_Java.calendarappapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event{
    long id;
    String title;
    String description;
    String location;
    String url;
    String type;
    Time startTime;
    Time endTime;
}
