package com.rishi.compare_product_website.service.features.getInfoForCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.compare_product_website.model.ProductInfo;
import com.rishi.compare_product_website.repository.ProductRepository;

@Service
public class GetPriceRangesForCategory {
	@Autowired
	private ProductRepository infoRepository;

	public List<String> getPriceRangesForCategory(String category) {
		List<String> ranges = Arrays.asList("501 - 1,000", "1,001 - 1,500", "1,501 - 3,000", "3,001 - 5,000",
				"5,001 - 10,000", "10,001 - 20,000", "20,001 - 30,000", "30,001 - 40,000", "40,001 - 50,000",
				"50,001 - 60,000", "60,001 - 70,000", "70,001 - 80,000", "80,001 - 90,000", "90,001 - 1,00,000",
				"1,00,001 - 2,00,000", "2,00,001 - 5,00,000");

		List<String> priceRanges = new ArrayList<>();

		List<Integer> prices = infoRepository.findPricesByCategory(category);

		if (prices.isEmpty()) {
			List<ProductInfo> products = infoRepository.findByNameContainingIgnoreCase(category);
			if (!products.isEmpty()) {
				prices = products.stream().map(ProductInfo::getPrice).collect(Collectors.toList());
			}
		}

		if (prices.isEmpty()) {
			return priceRanges;
		}

		Collections.sort(prices);

		int lowestPrice = prices.get(0);
		int highestPrice = prices.get(prices.size() - 1);

		if (lowestPrice <= 500) {
			priceRanges.add("500 & Below");
		}
		if (highestPrice >= 500001) {
			priceRanges.add("5,00,001 & Above");
		}

		for (String range : ranges) {
			String[] subRangeParts = range.split("[-&]");
			int subLowerPrice = Integer.parseInt(subRangeParts[0].replaceAll("[^0-9]", ""));
			int subUpperPrice = Integer.parseInt(subRangeParts[1].replaceAll("[^0-9]", ""));

			if (lowestPrice <= subUpperPrice && highestPrice >= subLowerPrice) {
				priceRanges.add(range);
			}
		}

		return priceRanges;
	}
}
