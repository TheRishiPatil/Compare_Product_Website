package com.rishi.compare_product_website.service.features.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;

@Service
public class FilterProductsByBrandName {

	public List<ProductInfo> filterProductsByBrandName(List<ProductInfo> products, String brandName) {

		if (products.isEmpty()) {
			return products;
		}

		List<ProductInfo> filteredProducts = products.stream()
				.filter(product -> brandName.equalsIgnoreCase(product.getBrandName())).collect(Collectors.toList());

		return filteredProducts;

	}
}