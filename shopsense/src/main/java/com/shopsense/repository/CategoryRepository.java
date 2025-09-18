package com.shopsense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopsense.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	boolean existsByTitle(String title);
}
