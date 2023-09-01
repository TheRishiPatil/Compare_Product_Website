package com.rishi.compare_product_website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rishi.compare_product_website.model.ProductCategory;

public interface CategoryRepository extends JpaRepository<ProductCategory, Long> {

	List<ProductCategory> findByCategory(String category);
	
}