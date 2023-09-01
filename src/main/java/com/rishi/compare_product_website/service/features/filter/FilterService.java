package com.rishi.compare_product_website.service.features.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;

@Service
public class FilterService {

	@Autowired
	private FilterProductsByPriceRange filterProductByPriceRange;

	@Autowired
	private FilterProductsByBrandName filterProductsByBrand;

	@Autowired
	private FilterProductsByPlatform filterProductsByPlatform;

	public List<ProductInfo> filterProductsByPriceRanges(List<ProductInfo> Products, List<String> selectedPriceRange) {
		List<ProductInfo> filteredProducts = new ArrayList<>();

		for (String priceRange : selectedPriceRange) {
			filteredProducts.addAll(filterProductByPriceRange.filterProductsByPriceRange(Products, priceRange));
		}
		return filteredProducts;
	}

	public List<ProductInfo> filterProductsByBrandName(List<ProductInfo> products, List<String> selectedbrandNames) {
		List<ProductInfo> filteredProducts = new ArrayList<>();

		for (String brandName : selectedbrandNames) {
			filteredProducts.addAll(filterProductsByBrand.filterProductsByBrandName(products, brandName));
		}

		return filteredProducts;
	}

	public List<ProductInfo> filterProductsByPlatform(List<ProductInfo> products, List<String> selectedcompanyNames) {
		List<ProductInfo> filteredProducts = new ArrayList<>();

		for (String platform : selectedcompanyNames) {
			filteredProducts.addAll(filterProductsByPlatform.filterProductsByPlatform(products, platform));
		}

		return filteredProducts;
	}
}
