package com.rishi.compare_product_website.service.features.compare;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;
import com.rishi.compare_product_website.repository.ProductRepository;

@Service
public class CompareProductInPlatforms {
	@Autowired
	private ProductRepository infoRepository;

	@Autowired
	private ProductComparison compare;

	public List<ProductInfo> compareProductAcrossPlatforms(String productName, String brandName) {
		List<ProductInfo> comparedProducts = new ArrayList<>();

		comparedProducts.add(getProduct(productName, brandName, "Flipkart"));
		comparedProducts.add(getProduct(productName, brandName, "Amazon"));
		comparedProducts.add(getProduct(productName, brandName, "Croma"));
		comparedProducts.add(getProduct(productName, brandName, "JioMart"));

		return comparedProducts;
	}

	public ProductInfo getProduct(String productName1, String brandName, String platformName) {
		List<ProductInfo> list = infoRepository.findByBrandAndPlatformIgnoreCase(brandName, platformName);

		for (ProductInfo each : list) {
			String productName2 = each.getName();

			if (compare.checkProduct(productName1, productName2)) {
				System.out.println(true);
				return each;
			}
		}

		return null;
	}

}