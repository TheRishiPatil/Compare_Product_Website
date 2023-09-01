package com.rishi.compare_product_website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rishi.compare_product_website.model.ProductInfo;

public interface ProductRepository extends JpaRepository<ProductInfo, Long> {

	List<ProductInfo> findByNameContainingIgnoreCase(String name);

	@Query("SELECT pi FROM ProductInfo pi JOIN pi.categories pc WHERE LOWER(pi.brandName) = LOWER(:brandName) "
			+ "AND LOWER(pi.companyName) = LOWER(:platformName)")
	List<ProductInfo> findByBrandAndPlatformIgnoreCase(String brandName, String platformName);

	Optional<ProductInfo> findByproductUrl(String productUrl);

	@Query("SELECT p.price FROM ProductInfo p JOIN p.categories c WHERE c.category = :category")
	List<Integer> findPricesByCategory(@Param("category") String category);

	@Query("SELECT p.brandName FROM ProductInfo p JOIN p.categories c WHERE c.category = :category")
	List<String> findBrandsByCategory(@Param("category") String category);

	@Query("SELECT p.companyName FROM ProductInfo p JOIN p.categories c WHERE c.category = :category")
	List<String> findPlatformByCategory(@Param("category") String category);

}