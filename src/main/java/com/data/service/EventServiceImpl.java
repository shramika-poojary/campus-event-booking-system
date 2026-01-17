package com.data.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.data.exception.ListIsEmptyException;
import com.data.exception.ResourceNotFoundException;
import com.data.model.Category;
import com.data.model.Event;
import com.data.model.User;
import com.data.repository.BookingRepository;
import com.data.repository.CategoryRepository;
import com.data.repository.EventRepository;
import com.data.repository.PaymentRepository;
import com.data.repository.TicketRepository;

import lombok.Data;

@Service
@Data
public class EventServiceImpl implements EventService  {

	@Autowired 
	private EventRepository repo;
	@Autowired
	private CategoryRepository categoryRepo;
	
	//working
	@Override
	public Event addEvent(Event event,int categoryId) {
		 Optional<Category> category = categoryRepo.findById(categoryId);
				 if(!category.isPresent()) {
					 throw new ResourceNotFoundException("Category not found"); 
				 }
		event.setCategory(category.get());
		Event saved=repo.save(event);
		if(saved!=null) {
			return saved;
		}else {
			throw new NullPointerException("failed to save event");
		}	
	}
	

	@Override
	public Event updateEventById(Event event, int eventId) {
		Optional<Event> existing = repo.findById(eventId);
		if(!existing.isPresent()) {
			throw new ResourceNotFoundException("Event not found"); 
		}else {
			Event existingEvent = existing.get();
			existingEvent.setTitle(event.getTitle());
			existingEvent.setDescription(event.getDescription());
		    existingEvent.setRules(event.getRules());
		    existingEvent.setVenue(event.getVenue());
		    existingEvent.setDate(event.getDate());
		    existingEvent.setTime(event.getTime());
		    existingEvent.setPrice(event.getPrice());
		    existingEvent.setPosterImg(event.getPosterImg());
		    return repo.save(existingEvent);
		}
	}

	@Override
	public boolean deleteEventById(int eventId) {
		Optional<Event> existing= repo.findById(eventId);
		if(!existing.isPresent()) {
			throw new ResourceNotFoundException("Incorrect event Id");
		}else {
			repo.deleteById(eventId);
			return true;
		}
		
	}

	@Override
	public List<Event> getEventsByCategoryId(int categoryId) {
		List<Event> event=repo.findByCategoryCategoryId(categoryId);
		return event;
		
	}

	@Override
	public Event getEventById(int eventId) {
		Optional<Event> event= repo.findById(eventId);
		if(!event.isPresent()) {
			throw new ResourceNotFoundException("Incorrect event Id");
		}
		return event.get();
	}

	@Override
	public List<Event> getAllEvents() {
		List<Event> events=repo.findAll();
		if(!events.isEmpty()) {
			return events;
		}else{
			throw new ListIsEmptyException("List is empty");
		}
	}

	//all upcoming events
	@Override
	public List<Event> getUpcomingEvents() {
		LocalDate today = LocalDate.now();
	    LocalTime now = LocalTime.now();
	    
	    List<Event> allEvents = repo.findAll();
	    List<Event> upcoming = new ArrayList<>();
	    
	    for (Event e : allEvents) {
	        // Event is in future
	        if (e.getDate().isAfter(today)) {
	            upcoming.add(e);
	        }
	        // Event is today but time is still left
	        else if (e.getDate().isEqual(today) && e.getTime().isAfter(now)) {
	            upcoming.add(e);
	        }
	        
	    }
	    
	    if (upcoming.isEmpty()) {
	        throw new ListIsEmptyException("No upcoming events");
	    }

	    return upcoming;
	}

	//only three events
	@Override
	public List<Event> getLimitedUpcomingEvents() {
		List<Event> upcoming = getUpcomingEvents();
	    return upcoming.stream().limit(3).toList();
	}
	
	
	
	@Override
	public List<Event> getCompletedEvents() {
		LocalDate today = LocalDate.now();
	    LocalTime now = LocalTime.now();
	    
	    List<Event> allEvents = repo.findAll();
	    List<Event> completed = new ArrayList<>();
	    
	    for (Event e : allEvents) {
	        // Event was before today
	        if (e.getDate().isBefore(today)) {
	            completed.add(e);
	        }
	        // Event is today but time is over
	        else if (e.getDate().isEqual(today) && e.getTime().isBefore(now)) {
	            completed.add(e);
	        }
	    }

	    if (completed.isEmpty()) {
	        throw new ListIsEmptyException("No completed events");
	    }
	    
	    return completed;
	}

	@Override
	public Event save(Event event) {
		return repo.save(event);
	}

	@Override
	public Event getEventBytitle(String title) {
		Event event= repo.findByTitleContainingIgnoreCase(title);
	
		return event;
	}

	

}
