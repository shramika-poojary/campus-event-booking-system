package com.data.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.data.dto.EventUpdateDTO;
import com.data.model.Event;

public interface EventService {

	//Event addEvent(Event event);
	
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

	Event addEvent(Event event, int categoryId);

	//Event updateEventById(EventUpdateDTO dto, int eventId);

	Event getEventBytitle(String title);

	//Event addEvent(Event event, int categoryId, MultipartFile image) throws IOException;
	
	
    }

	

