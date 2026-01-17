package com.data.service;

import java.util.List;

import com.data.model.Category;

public interface CategoryService {

	Category addCategory(Category category);
	
	Category updateCategoryById(Category category, int categoryId);
	
	boolean deleteCategoryById(int categoryId);
	
	Category getCategoryByName(String categoryName);

	List<Category> getAllCategories();  //homepage/admin
	
	Category getCategoryById(int categoryId);
}
