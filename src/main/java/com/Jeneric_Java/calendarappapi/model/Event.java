package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
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
    String closestCity;

    @Column(name = "start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime;
}
