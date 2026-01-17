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

import com.data.model.Category;
import com.data.service.CategoryServiceImpl;

@CrossOrigin(
	    origins = "http://localhost:5500",
	    allowCredentials = "true"
	)
@RestController
@RequestMapping("api/category")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl service;
	
	@GetMapping("/get/categories")
	public ResponseEntity<?> get_all_categories(){
		List<Category> categories=service.getAllCategories();
		return new ResponseEntity<>(categories,HttpStatus.OK);
	}
	
	@GetMapping("/get/categorybyname/{categoryName}")
	public ResponseEntity<?> get_category_by_name(@PathVariable String categoryName){
		Category category=service.getCategoryByName(categoryName);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
}
