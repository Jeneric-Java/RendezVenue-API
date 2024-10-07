package com.Jeneric_Java.calendarappapi.model;

import com.Jeneric_Java.calendarappapi.service.location.utilities.LocationSet;
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
    Long id;

    @Getter
    @Column(name = "event_title", nullable = false)
    String title;

    @Column(name = "description", nullable = true)
    String description;

    @Column(name = "location", nullable = false)
    String location;

    @Column(name = "event_url", nullable = true)
    String url;

    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    EventType type;

    @Column(name = "closest_city", nullable = false)
    LocationSet closestCity;

    @Column(name = "start_time", nullable = true)
    String startTime;

    @Column(name = "start_date", nullable = false)
    String startDate;

    @Column(name = "end_time", nullable = true)
    String endTime;

    @Column(name = "end_date", nullable = true)
    String endDate;
}
