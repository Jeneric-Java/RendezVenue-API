package com.Jeneric_Java.calendarappapi.controller;

import com.Jeneric_Java.calendarappapi.model.ApiEvent;
import com.Jeneric_Java.calendarappapi.model.Time;
import org.springframework.stereotype.Controller;

import java.text.ParseException;

@Controller
public class Parser {

    public Time parseTime(ApiEvent.Dates.Date input) throws ParseException {
        if (input == null || input.localTime() == null || input.localDate() == null) throw new IllegalArgumentException("Error while parsing event date/time! Date/Time cannot be null!");

        String[] date = input.localDate().split("-");
        String[] time = input.localTime().split(":");

        try {
            return new Time(
                    null,
                    Integer.parseInt(date[0]),
                    Integer.parseInt(date[1]),
                    Integer.parseInt(date[2]),
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1])
            );
        } catch (NumberFormatException e) {
            throw new ParseException("Error while parsing event date/time!", -1);
        }
    }
}
