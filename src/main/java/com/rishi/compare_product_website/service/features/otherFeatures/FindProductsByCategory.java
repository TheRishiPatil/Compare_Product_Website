package com.rishi.compare_product_website.service.features.otherFeatures;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductCategory;
import com.rishi.compare_product_website.model.ProductInfo;
import com.rishi.compare_product_website.repository.CategoryRepository;
import com.rishi.compare_product_website.repository.ProductRepository;

@Service
public class FindProductsByCategory {
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository infoRepository;

	private String finalCategory;

	public String getFinalCategory() {
		return finalCategory;
	}

	public void setFinalCategory(String finalCategory) {
		this.finalCategory = finalCategory;
	}

	public List<ProductInfo> findProductsByCategory(String category) {
		List<ProductInfo> products = new ArrayList<>();
		List<ProductCategory> productCategories = categoryRepository.findByCategory(category);
		setFinalCategory(category);

		if (productCategories.isEmpty()) {
			products = infoRepository.findByNameContainingIgnoreCase(category);
			if (!products.isEmpty()) {
				setFinalCategory(products.get(0).getName());
			}

			if (products.isEmpty()) {
				List<ProductCategory> allCategories = categoryRepository.findAll();
				LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

				for (ProductCategory productCategory : allCategories) {
					if (levenshteinDistance.apply(category, productCategory.getCategory()) <= 2) {
						products.addAll(findProductsByCategory(productCategory.getCategory()));
						setFinalCategory(productCategory.getCategory());
					}
				}
			}
		} else {
			for (ProductCategory productCategory : productCategories) {
				products.add(productCategory.getProduct());
			}
		}
		return products;
	}
}