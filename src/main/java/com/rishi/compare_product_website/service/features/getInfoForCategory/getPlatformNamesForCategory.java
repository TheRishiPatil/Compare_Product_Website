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
public class getPlatformNamesForCategory {
	@Autowired
	private ProductRepository infoRepository;

	public List<String> getCompanyNames(String category) {
		List<String> temp = infoRepository.findPlatformByCategory(category);
		Set<String> removeDuplicates = new HashSet<>();

		for (String string : temp) {
			removeDuplicates.add(string);
		}

		List<String> Platforms = new ArrayList<>(removeDuplicates);

		if (Platforms.isEmpty()) {
			List<ProductInfo> products = infoRepository.findByNameContainingIgnoreCase(category);
			if (!products.isEmpty()) {
				Platforms = products.stream().map(ProductInfo::getCompanyName).collect(Collectors.toList());
			}
		}

		return Platforms;

	}
}
