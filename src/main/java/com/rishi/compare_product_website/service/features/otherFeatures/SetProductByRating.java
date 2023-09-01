package com.rishi.compare_product_website.service.features.otherFeatures;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;

@Service
public class SetProductByRating {
	public List<ProductInfo> sortProductsByRating(List<ProductInfo> products) {
		List<ProductInfo> sortedProducts = products.stream()
				.sorted(Comparator.comparing(ProductInfo::getRating, Comparator.reverseOrder()))
				.collect(Collectors.toList());

		return sortedProducts;
	}
}