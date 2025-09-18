package com.shopsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopsense.entity.Category;
import com.shopsense.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category getCategory(int categoryId) {
		return categoryRepository.findById(categoryId)
			.orElseThrow(() -> new RuntimeException("Category not found"));
	}

	public Category createCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Category updateCategory(Category category) {
		return categoryRepository.save(category);
	}

	public boolean deleteCategory(int categoryId) {
		try {
			categoryRepository.deleteById(categoryId);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean existsByTitle(String title) {
		return categoryRepository.existsByTitle(title);
	}
}
