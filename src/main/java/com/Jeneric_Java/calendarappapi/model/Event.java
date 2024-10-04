package com.Jeneric_Java.calendarappapi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
@Entity
@Table(name = "Events")
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

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    Set<Time> times = new HashSet<>();




}
