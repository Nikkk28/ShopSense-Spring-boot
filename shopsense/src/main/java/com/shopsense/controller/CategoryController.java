package com.shopsense.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsense.service.CategoryService;
import com.shopsense.entity.Category;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@GetMapping(value = "/category/all")
	public List<Category> getCategories() {
		return categoryService.getAllCategories();
	}

	@PostMapping(value = "/category")
	public Category create(@RequestBody Category c) {
		return categoryService.createCategory(c);
	}

	@PutMapping(value = "/category")
	public boolean update(@RequestBody Category c) {
		return categoryService.updateCategory(c);
	}

	@DeleteMapping(value = "/category")
	public boolean delete(@RequestParam int id) {
		return categoryService.deleteCategory(id);
	}
}
