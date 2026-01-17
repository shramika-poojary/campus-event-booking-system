package com.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.exception.ListIsEmptyException;
import com.data.exception.ResourceNotFoundException;
import com.data.model.Category;
import com.data.repository.BookingRepository;
import com.data.repository.CategoryRepository;
import com.data.repository.PaymentRepository;
import com.data.repository.TicketRepository;

import lombok.Data;

@Service
@Data
public class CategoryServiceImpl implements CategoryService {

	@Autowired CategoryRepository repo;

	@Override
	public Category addCategory(Category category) {
		Category saved = repo.save(category);
		if(saved!=null) {
			return saved;
		}else {
			throw new NullPointerException("failed to save category");
		}
		
	}

	@Override
	public Category updateCategoryById(Category category, int categoryId) {
		Optional<Category> existing= repo.findById(categoryId);
		if(!existing.isPresent()) {
			throw new ResourceNotFoundException("Incorrect Category Id");
		}else {
			Category c = existing.get();
			c.setCategoryName(category.getCategoryName());
			return repo.save(c);
		}
		
	}

	@Override
	public boolean deleteCategoryById(int categoryId) {
		Optional<Category> existing= repo.findById(categoryId);
		if(!existing.isPresent()) {
			throw new ResourceNotFoundException("Incorrect Category Id");
		}else {
			repo.deleteById(categoryId);
			return true;
		}
	}

	@Override
	public Category getCategoryByName(String categoryName) {
		Optional<Category> category=repo.findByCategoryName(categoryName);
		if(!category.isPresent()) {
			throw new ResourceNotFoundException("No Such category");
		}else{
			return category.get();
		}
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories=repo.findAll();
		if(!categories.isEmpty()) {
			return categories;
		}else{
			throw new ListIsEmptyException("List is empty");
		}
		
	}

	@Override
	public Category getCategoryById(int categoryId) {
		Optional<Category> category= repo.findById(categoryId);
		if(!category.isPresent()) {
			throw new ResourceNotFoundException("Incorrect Category Id");
		}
		return category.get();
	}
}
