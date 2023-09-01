package com.rishi.compare_product_website.service.features.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;

@Service
public class FilterProductsByPriceRange {

	public List<ProductInfo> filterProductsByPriceRange(List<ProductInfo> products, String priceRange) {

		if (products.isEmpty()) {
			return products;
		}

		String[] rangeParts = priceRange.split("[-&]");

		int lowerBound = Integer.parseInt(rangeParts[0].replaceAll("[^0-9]", ""));

		if (rangeParts[1].equals(" Above")) {
			int upperBound = Integer.MAX_VALUE;

			List<ProductInfo> filteredProducts = products.stream()
					.filter(product -> product.getPrice() >= lowerBound && product.getPrice() <= upperBound)
					.collect(Collectors.toList());

			return filteredProducts;

		} else if (rangeParts[1].equals(" Below")) {
			int upperBound = 0;

			List<ProductInfo> filteredProducts = products.stream()
					.filter(product -> product.getPrice() <= lowerBound && product.getPrice() >= upperBound)
					.collect(Collectors.toList());

			return filteredProducts;
		} else {
			int upperBound = Integer.parseInt(rangeParts[1].replaceAll("[^0-9]", ""));
			List<ProductInfo> filteredProducts = products.stream()
					.filter(product -> product.getPrice() >= lowerBound && product.getPrice() <= upperBound)
					.collect(Collectors.toList());

			return filteredProducts;
		}
	}
}