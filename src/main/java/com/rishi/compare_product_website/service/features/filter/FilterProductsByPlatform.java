package com.rishi.compare_product_website.service.features.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;

@Service
public class FilterProductsByPlatform {

	public List<ProductInfo> filterProductsByPlatform(List<ProductInfo> products, String selectedPlatform) {

		if (products.isEmpty()) {
			return products;
		}

		List<ProductInfo> filteredProducts = products.stream()
				.filter(product -> product.getCompanyName().equalsIgnoreCase(selectedPlatform))
				.collect(Collectors.toList());

		return filteredProducts;
	}
}