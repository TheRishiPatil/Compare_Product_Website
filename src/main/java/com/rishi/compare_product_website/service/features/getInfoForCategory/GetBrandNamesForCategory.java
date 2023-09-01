package com.rishi.compare_product_website.service.features.getInfoForCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;
import com.rishi.compare_product_website.repository.ProductRepository;

@Service
public class GetBrandNamesForCategory {
	@Autowired
	private ProductRepository infoRepository;

	public List<String> getBrandNamesForCategory(String category) {
		List<String> temp = infoRepository.findBrandsByCategory(category);
		Set<String> removeDuplicates = new HashSet<>();

		for (String string : temp) {
			removeDuplicates.add(string);
		}

		List<String> brandNames = new ArrayList<>(removeDuplicates);

		if (brandNames.isEmpty()) {
			List<ProductInfo> products = infoRepository.findByNameContainingIgnoreCase(category);
			if (!products.isEmpty()) {
				brandNames = products.stream().map(ProductInfo::getBrandName).collect(Collectors.toList());
			}
		}

		return brandNames;
	}
}
