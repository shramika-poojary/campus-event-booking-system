package com.data.service;

import java.util.List;

import com.data.model.Event;

public interface EventService {

	Event addEvent(Event event);
	
	Event updateEventById(Event event, int eventId);
	
	boolean deleteEventById(int eventId);
	
	List<Event> getEventsByCategoryId(int categoryId);
	
	Event getEventById(int eventId);
	
	List<Event> getAllEvents();  
	
	List<Event> getUpcomingEvents();  
	List<Event> getLimitedUpcomingEvents();  
	
	//List<Event> searchEvents(String keyword)

	List<Event> getCompletedEvents();

	Event save(Event event);

	
}
