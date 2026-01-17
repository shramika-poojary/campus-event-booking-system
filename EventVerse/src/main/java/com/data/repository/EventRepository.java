package com.data.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Event;

public interface EventRepository  extends JpaRepository <Event, Integer>  {

	List<Event> findByCategoryCategoryId(int categoryId);  // to get all events in category
	 List<Event> findByTitleContainingIgnoreCase(String title); // search events by keyword
	 List<Event> findByDateAfter(LocalDate date); //for upcoming events
	 List<Event> findByTimeAfter(LocalTime time); //for upcoming events
	 List<Event> findByDateBefore(LocalDate date); //for completed events
	 List<Event> findByTimeBefore(LocalTime now);
	 
}
