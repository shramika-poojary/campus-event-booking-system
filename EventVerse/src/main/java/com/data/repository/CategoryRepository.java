package com.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.model.Category;
import com.data.model.Event;

public interface CategoryRepository extends JpaRepository <Category, Integer> {

	Optional<Category> findByCategoryName(String categoryName);

	 
}
