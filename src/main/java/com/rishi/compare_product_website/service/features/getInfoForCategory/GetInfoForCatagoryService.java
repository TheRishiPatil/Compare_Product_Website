package com.rishi.compare_product_website.service.features.getInfoForCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetInfoForCatagoryService {

	@Autowired
	private GetPriceRangesForCategory priceRange;

	@Autowired
	private GetBrandNamesForCategory brandNames;

	@Autowired
	private getPlatformNamesForCategory platformNames;

	public List<String> getPriceRangesForCategory(String category) {
		return removeDuplicates(priceRange.getPriceRangesForCategory(category));
	}

	public List<String> getBrandNamesForCategory(String category) {
		return removeDuplicates(brandNames.getBrandNamesForCategory(category));
	}

	public List<String> getPlatformsForCategory(String category) {
		return removeDuplicates(platformNames.getCompanyNames(category));
	}

	public static List<String> removeDuplicates(List<String> filter) {
		Set<String> uniqueStrings = new HashSet<>();
		List<String> result = new ArrayList<>();

		for (String string : filter) {
			String lowercaseString = string.toLowerCase();

			if (!uniqueStrings.contains(lowercaseString)) {
				uniqueStrings.add(lowercaseString);
				result.add(string);
			}
		}

		return result;
	}

}
