package com.Jeneric_Java.calendarappapi.repository;

import com.Jeneric_Java.calendarappapi.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface ApiRepository extends CrudRepository<Event, Long> {
}
