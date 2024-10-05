package com.Jeneric_Java.calendarappapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//    @Column(name = "start_time", nullable = false)
//    String startTime;
//
//    @Column(name = "end_time", nullable = false)
//    String endTime;

//    @Column(name = "start_date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-hh-mm")
//    LocalDateTime startDate;
//
//    @Column(name = "end_date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-hh-mm")
//    LocalDateTime endDate;


    @Column(name = "start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime;



//    @JsonIgnore
//    @Embedded
//    @OneToMany(mappedBy = "event",  cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//    private Set<Time> times = new HashSet<>();

//    public void addTime(Time startTime, Time endTime) {
//
//        startTime.setEvent(this);
//        endTime.setEvent(this);
//        this.times.add(startTime);
//        this.times.add(endTime);
//    }
//
//    public Event(Long id, String title, String description, String location, String url, EventType type, String closestCity, Set<Time> times) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.location = location;
//        this.url = url;
//        this.type = type;
//        this.closestCity = closestCity;
//        this.times = times;
//    }

//    public void setTimes(Set<Time> times) {
//        this.times = associateTimes(times);
//    }
//
//    private Set<Time> associateTimes(Set<Time> times) {
////        for (Time time : times) {
////            time.setEvent(this);
////        }
//        return times;
//    }
}
