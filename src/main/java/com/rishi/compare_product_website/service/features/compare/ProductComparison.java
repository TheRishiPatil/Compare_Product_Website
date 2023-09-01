package com.rishi.compare_product_website.service.features.compare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ProductComparison {

	public boolean checkProduct(String productName1, String productName2) {

		List<String> words1 = normalizeAndSplit(productName1);
		List<String> words2 = normalizeAndSplit(productName2);
		
//		for(String word : words1) {
//			System.out.print(word + " ");
//		}
//		System.out.print(" <-- selected product");
//		System.out.println();
//		
//		for(String word : words2) {
//			System.out.print(word + " ");
//		}
//		System.out.println();

		Map<String, Integer> map1 = mapProduct(words1);
		Map<String, Integer> map2 = mapProduct(words2);

		return map1.equals(map2);
	}

	private static List<String> normalizeAndSplit(String productName) { // god chatgpt solution
		String[] wordsInArray = productName.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");

		List<String> result = new ArrayList<>();

		for (String word : wordsInArray) {
			String[] parts = word.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
			result.addAll(Arrays.asList(parts));
		}

		return result;
	}

	/* my solution 
	private static List<String> normalizeAndSplit(String productName) {
		String[] wordsInArray = productName.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
		List<String> result = new ArrayList<>();

		for (String word : wordsInArray) {
			String word1 = "";
			String word2 = "";
			for (int i = 0; i < word.length() - 1; i++) {
				if ((Character.isLetter(word.charAt(i)) && Character.isDigit(word.charAt(i + 1)))
						|| (Character.isDigit(word.charAt(i)) && Character.isLetter(word.charAt(i + 1)))) {
					word1 = word.substring(0, i + 1);
					word2 = word.substring(i + 1);
					break;
				}
			}
			if (word1 != "") {
				result.add(word1);
				result.add(word2);
			} else {
				result.add(word);
			}
		}

		System.out.println(result.toString());

		return result;
	}
	 */

	private static Map<String, Integer> mapProduct(List<String> words) {
		Map<String, Integer> map = new HashMap<>();
		for (String word : words) {
			map.put(word, map.getOrDefault(word, 0) + 1);
		}
		return map;
	}
}
