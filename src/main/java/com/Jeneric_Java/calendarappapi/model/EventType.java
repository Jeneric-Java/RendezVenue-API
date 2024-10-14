package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum EventType {
    @JsonAlias("Arts & Theatre")
    ART_THEATRE("Arts & Theatre"),

    @JsonAlias("Miscellaneous")
    MISC("Miscellaneous"),

    @JsonAlias("Sports")
    SPORT("Sports"),

    @JsonAlias("Music")
    MUSIC("Music"),

    @JsonAlias("Film")
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