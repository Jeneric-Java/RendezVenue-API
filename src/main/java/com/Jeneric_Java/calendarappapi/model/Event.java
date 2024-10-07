package com.Jeneric_Java.calendarappapi.model;

import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Entity
@Table(name = "Events")
@Embeddable
public class Event implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "event_id", updatable = false, nullable = false)
    @Schema(name = "id", description = "Event id, for events in our database", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;

    @Getter
    @Column(name = "event_title", nullable = false)
    @Schema(name = "title", description = "Name of the event", example = "Example Event")
    String title;

    @Column(name = "description", nullable = true)
    @Schema(name = "description", description = "Short description of the event", example = "Event @ The Venue")
    String description;

    @Column(name = "location", nullable = false)
    @Schema(name = "location", description = "Location of the event, generally a postcode", example = "SW1A 1AA")
    String location;

    @Column(name = "event_url", nullable = true)
    @Schema(name = "url", description = "Link to website with more info on event", example = "example.com", nullable = true)
    String url;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(name = "eventType", description = "What category the event falls under", example = "MUSIC", nullable = false,
            allowableValues = {"ART_THEATRE", "MISC", "SPORT", "MUSIC", "FILM"})
    EventType type;

    @Column(name = "closest_city", nullable = false)
    @Schema(name = "closestCity", description = "Closest city we track to event location", example = "LONDON", nullable = false)
    LocationSet closestCity;

    @Column(name = "start_time", nullable = true)
    @Schema(name = "startTime", description = "Time the event starts, formatted HH:MM:SS/HH:MM", example = "10:00:00", nullable = true)
    String startTime;

    @Column(name = "start_date", nullable = false)
    @Schema(name = "startDate", description = "Date the event starts on, formatted YYYY-MM-DD", example = "2024-10-01", nullable = false)
    String startDate;

    @Column(name = "end_time", nullable = true)
    @Schema(name = "endTime", description = "Time the event ends, if provided. Formatted HH:MM:SS/HH:MM", example = "12:00:00", nullable = true)
    String endTime;

    @Column(name = "end_date", nullable = true)
    @Schema(name = "endDate", description = "Date the event ends, if provided. Formatted YYYY-MM-DD", example = "2024-10-02", nullable = true)
    String endDate;
}