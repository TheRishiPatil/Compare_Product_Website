package com.rishi.compare_product_website.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;
import com.rishi.compare_product_website.service.features.compare.CompareProductsService;
import com.rishi.compare_product_website.service.features.filter.FilterService;
import com.rishi.compare_product_website.service.features.getInfoForCategory.GetInfoForCatagoryService;
import com.rishi.compare_product_website.service.features.otherFeatures.FindProductsByCategory;
import com.rishi.compare_product_website.service.features.otherFeatures.SetProductByRating;

@Service
public class ServiceController {

	private List<ProductInfo> products;
	private String finalCatagory;

	@Autowired
	private FindProductsByCategory productsByCategory;

	public void findProductsByCategory(String category) {
		products = productsByCategory.findProductsByCategory(category);
		finalCatagory = productsByCategory.getFinalCategory();
	}

	@Autowired
	private SetProductByRating sortProducts;

	public List<ProductInfo> sortProductList() {
		products = sortProducts.sortProductsByRating(products);
		return products;
	}

	@Autowired
	private GetInfoForCatagoryService getInfo;

	public List<String> getPriceRange() {
		return getInfo.getPriceRangesForCategory(finalCatagory);
	}

	public List<String> getBrandName() {
		return getInfo.getBrandNamesForCategory(finalCatagory);
	}

	public List<String> getPlatform() {
		return getInfo.getPlatformsForCategory(finalCatagory);
	}

	@Autowired
	private FilterService filterService;

	public List<ProductInfo> filterByPriceRange(List<String> selectedPriceRange, List<ProductInfo> resultList) {
		if (selectedPriceRange.get(0).length() < 3 || selectedPriceRange.get(0).equals("501 - 1")) {
			String tempString = "";
			for (String string : selectedPriceRange) {
				tempString = tempString + string;
			}
			selectedPriceRange.clear();
			selectedPriceRange.add(tempString);
		}
		return filterService.filterProductsByPriceRanges(resultList, selectedPriceRange);
	}

	public List<ProductInfo> filterByBrandName(List<String> selectedbrandNames, List<ProductInfo> resultList) {
		return filterService.filterProductsByBrandName(resultList, selectedbrandNames);
	}

	public List<ProductInfo> filterByPlatform(List<String> selectedcompanyNames, List<ProductInfo> resultList) {
		return filterService.filterProductsByPlatform(resultList, selectedcompanyNames);
	}

	public List<ProductInfo> filterList(List<String> selectedPriceRange, List<String> selectedbrandNames,
			List<String> selectedcompanyNames) {
		List<ProductInfo> resultList = new ArrayList<>();
		resultList = products;

		if (selectedPriceRange != null && !selectedPriceRange.isEmpty()) {
			resultList = filterByPriceRange(selectedPriceRange, resultList);
		}

		if (selectedbrandNames != null && !selectedbrandNames.isEmpty()) {
			resultList = filterByBrandName(selectedbrandNames, resultList);
		}

		if (selectedcompanyNames != null && !selectedcompanyNames.isEmpty()) {
			resultList = filterByPlatform(selectedcompanyNames, resultList);
		}
		return resultList;

	}

	public List<ProductInfo> sortFilterProductList(List<ProductInfo> result) {
		return sortProducts.sortProductsByRating(result);
	}

	@Autowired
	private CompareProductsService compareProducts;

	public List<ProductInfo> compareProduct(String productName, String brandName) {
		List<ProductInfo> comparedProducts = compareProducts.compareProduct(productName, brandName);
		comparedProducts.add(findProductByPlatform(comparedProducts, "Flipkart"));
		comparedProducts.add(findProductByPlatform(comparedProducts, "Amazon"));
		comparedProducts.add(findProductByPlatform(comparedProducts, "Croma"));
		comparedProducts.add(findProductByPlatform(comparedProducts, "JioMart"));

		return comparedProducts;
	}

	private ProductInfo findProductByPlatform(List<ProductInfo> comparedProducts, String platform) {
		for (ProductInfo product : comparedProducts) {
			if (product != null) {
				if (product.getCompanyName().equalsIgnoreCase(platform)) {
					return product;
				}
			}
		}
		return null;
	}

	public void addToCompareList(String productUrl) {
		compareProducts.addToCompareList(productUrl);
	}

	public boolean limit() {
		return compareProducts.limit();
	}

	public List<ProductInfo> getCompareList() {
		return compareProducts.getCompareList();
	}

	public void removeFromCompareList(int index) {
		compareProducts.removeFromCompareList(index);
	}
}
