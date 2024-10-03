package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Times")
public class Time implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "time_id", updatable = false, nullable = false)
    Long id;

    @Column(name = "date_year", nullable = false)
    Integer year;

    @Column(name = "date_month", nullable = false)
    Integer month;

    @Column(name = "date_day", nullable = false)
    Integer day;

    @Column(name = "time_hour", nullable = true)
    Integer hour;

    @Column(name = "time_minute", nullable = true)
    Integer minute;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event", referencedColumnName = "event_id")
    Event event;
}
