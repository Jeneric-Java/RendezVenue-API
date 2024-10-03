package com.Jeneric_Java.calendarappapi.repository;

import com.Jeneric_Java.calendarappapi.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends CrudRepository<Event, Long> {




}
