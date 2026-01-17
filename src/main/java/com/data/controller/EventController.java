package com.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data.model.Event;
import com.data.service.EventServiceImpl;
@CrossOrigin(
	    origins = "http://localhost:5500",
	    allowCredentials = "true"
	)
@RestController
@RequestMapping("api/event")
public class EventController {

	@Autowired
	private EventServiceImpl service;
	
	@GetMapping("/geteventbycategory/{categoryId}")
	public ResponseEntity<?> get_all_events_by_catrgories(@PathVariable int categoryId){
		List<Event> events=service.getEventsByCategoryId(categoryId);
		return new ResponseEntity<>(events,HttpStatus.OK);
	}
	
	@GetMapping("/getevent/{eventId}")
	public ResponseEntity<?> get_event(@PathVariable int eventId){
		Event event=service.getEventById(eventId);
		return new ResponseEntity<>(event,HttpStatus.OK);
	}
	
	@GetMapping("/getupcomingevents")
	public ResponseEntity<?> get_upcoming_events(){
		List<Event> events=service.getLimitedUpcomingEvents();
		return new ResponseEntity<>(events,HttpStatus.OK);
	}
	
	@GetMapping("/geteventbyname/{title}")
	public ResponseEntity<?> get_event_by_name(@PathVariable String title){
		Event event=service.getEventBytitle(title);
		return new ResponseEntity<>(event,HttpStatus.OK);
	}
}
