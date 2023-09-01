package com.rishi.compare_product_website.service.features.compare;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;
import com.rishi.compare_product_website.repository.ProductRepository;

@Service
public class CompareProductsInList {
	private List<ProductInfo> comparedProductsList = new ArrayList<>();
	@Autowired
	private ProductRepository infoRepository;

	public void addToCompareList(String productUrl) {
		if (limit()) {
			ProductInfo product = infoRepository.findByproductUrl(productUrl).get();
			if (product != null) {
				if (isPresent(comparedProductsList, productUrl) == false) {
					comparedProductsList.add(product);
				}
			}
		}
	}

	public boolean limit() {
		if (comparedProductsList.size() < 5) {
			return true;
		}
		return false;
	}

	public boolean isPresent(List<ProductInfo> comparedProductsList, String productUrl) {
		for (ProductInfo eachProduct : comparedProductsList) {
			if (eachProduct.getProductUrl().equals(productUrl)) {
				return true;
			}
		}
		return false;
	}

	public List<ProductInfo> getCompareList() {
		return comparedProductsList;
	}

	public void removeFromCompareList(int index) {
		comparedProductsList.remove(index);
	}
}
