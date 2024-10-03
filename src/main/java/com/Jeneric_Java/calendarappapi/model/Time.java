package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Times")
public class Time {
    @Id
    @GeneratedValue
    @Column(name = "time_id", updatable = false, nullable = false)
    Long id;

    @Column(name = "year", nullable = false)
    Integer year;

    @Column(name = "month", nullable = false)
    Integer month;

    @Column(name = "day", nullable = false)
    Integer day;

    @Column(name = "hour", nullable = true)
    Integer hour;

    @Column(name = "minute", nullable = true)
    Integer minute;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "event_id")
    Event event;
}
