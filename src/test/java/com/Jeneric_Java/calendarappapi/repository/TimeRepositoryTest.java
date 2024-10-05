package com.Jeneric_Java.calendarappapi.repository;

import com.Jeneric_Java.calendarappapi.model.Event;
import com.Jeneric_Java.calendarappapi.model.EventType;
import com.Jeneric_Java.calendarappapi.model.Time;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TimeRepositoryTest {

    @Autowired
    TimeRepository timeRepository;

    @Test
    @DisplayName("Writes to, and reads from, H2 in-memory database three unconstrained instances of class Event")
    public void testGetAllEvents() {

        // Arrange
        List<Time> expectedTimeList = getTimeList();

        // Act
        timeRepository.saveAll(expectedTimeList);
        Iterable<Time> actualTimeList = timeRepository.findAll();

        // Assert
        assertThat(actualTimeList).hasSize(3);
        assertThat(expectedTimeList).isEqualTo(actualTimeList);
    }

    private static List<Time> getTimeList() {
        Time time1 = new Time(1L, 2018, 10, 4, 15, 30, true, null);
        Time time2 = new Time(2L, 2018, 10, 4, 16, 30, false, null);
        Time time3 = new Time(3L, 2020, 10, 3, 15, 30, true, null);

        List<Time> expectedTimeList = new ArrayList<>();
        expectedTimeList.add(time1);
        expectedTimeList.add(time2);
        expectedTimeList.add(time3);
        return expectedTimeList;
    }



}