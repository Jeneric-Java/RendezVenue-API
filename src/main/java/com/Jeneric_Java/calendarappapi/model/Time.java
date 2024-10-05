//package com.Jeneric_Java.calendarappapi.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Data
//@Entity
//@Table(name = "Times")
//@Embeddable
//public class Time implements Serializable {
//    @Id
//    @GeneratedValue
//    @Column(name = "time_id", updatable = false, nullable = false)
//    Long id;
//
//    @Column(name = "date_year", nullable = false)
//    Integer year;
//
//    @Column(name = "date_month", nullable = false)
//    Integer month;
//
//    @Column(name = "date_day", nullable = false)
//    Integer day;
//
//    @Column(name = "time_hour", nullable = true)
//    Integer hour;
//
//    @Column(name = "time_minute", nullable = true)
//    Integer minute;
//
//    @Column(name = "start_time", nullable = true)
//    Boolean isStartTime;
//
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER)
//    @Embedded
//    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = true)
//    private Event event;
//
//    public Time(Long id, Integer year, Integer month, Integer day, Integer hour, Integer minute, Boolean isStartTime) {
//        this.id = id;
//        this.year = year;
//        this.month = month;
//        this.day = day;
//        this.hour = hour;
//        this.minute = minute;
//        this.isStartTime = isStartTime;
//        this.event = new Event();
//    }
//
//    @Override
//    public String toString() {
//        return "Time{" +
//                "isStartTime=" + isStartTime +
//                ", id=" + id +
//                ", year=" + year +
//                ", month=" + month +
//                ", day=" + day +
//                ", hour=" + hour +
//                ", minute=" + minute +
//                '}';
//    }
//}
