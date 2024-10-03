package com.Jeneric_Java.calendarappapi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Events")
public class Event{

    @Id
    @GeneratedValue
    @Column(name = "event_id", updatable = false, nullable = false)
    Long id;

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

    @Column(name = "start_time", nullable = true)
    Time startTime;

    @Column(name = "end_time", nullable = true)
    Time endTime;

    @JsonIgnore
    @OneToMany(mappedBy = "event")
    Set<Time> times = new HashSet<>();
}
