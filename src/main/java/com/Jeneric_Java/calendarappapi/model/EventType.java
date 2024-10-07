package com.Jeneric_Java.calendarappapi.model;

public enum EventType {
    ART_THEATRE("Arts & Theatre"),
    MISC("Miscellaneous"),
    SPORT("Sports"),
    MUSIC("Music"),
    FILM("Film");

    final String text;

    EventType(String typeText) {
        this.text = typeText;
    }

    @Override
    public String toString() {
        return this.text;
    }
}