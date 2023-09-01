package com.rishi.compare_product_website.service.features.compare;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;

@Service
public class CompareProductsService {

	@Autowired
	private CompareProductInPlatforms compareProductInPlatforms;

	@Autowired
	private CompareProductsInList compareProductInList;

	public List<ProductInfo> compareProduct(String productName, String brandName) {
		return compareProductInPlatforms.compareProductAcrossPlatforms(productName, brandName);
	}

	public void addToCompareList(String productUrl) {
		compareProductInList.addToCompareList(productUrl);
	}

	public boolean limit() {
		return compareProductInList.limit();
	}

	public List<ProductInfo> getCompareList() {
		return compareProductInList.getCompareList();
	}

	public void removeFromCompareList(int index) {
		compareProductInList.removeFromCompareList(index);
	}
}
