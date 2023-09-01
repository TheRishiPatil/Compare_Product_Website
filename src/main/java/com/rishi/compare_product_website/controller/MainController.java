package com.rishi.compare_product_website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rishi.compare_product_website.model.ProductInfo;
import com.rishi.compare_product_website.service.ServiceController;

@Controller
public class MainController {

	private String finalCategory;
	private List<ProductInfo> products;
	private int currentPage = 0;

	@Autowired
	private ServiceController service;

	@GetMapping("/")
	public String mainPage() {
		return "homePage";
	}

	@RequestMapping(value = { "/search", "/search" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String searchProducts(@RequestParam("category") String category,
			@RequestParam(value = "page", defaultValue = "0") int page, Model model) {
		service.findProductsByCategory(category);
		products = service.sortProductList();
		currentPage = page;

		if (products.isEmpty()) {
			model.addAttribute("message", "No products found!!");
		}

		finalCategory = category;
		updateProductsAndPage(model);

		return "productListPage";
	}

	@RequestMapping(value = { "/filterProducts", "/filterProducts" }, method = { RequestMethod.GET,
			RequestMethod.POST })
	public String filterProducts(
			@RequestParam(value = "selectedPriceRange", required = false) List<String> selectedPriceRange,
			@RequestParam(value = "selectedBrandNames", required = false) List<String> selectedBrandNames,
			@RequestParam(value = "selectedPlatforms", required = false) List<String> selectedPlatforms,
			@RequestParam(value = "page", defaultValue = "0") int page, Model model) {

		List<ProductInfo> resultList = service.filterList(selectedPriceRange, selectedBrandNames, selectedPlatforms);

		if (products.isEmpty()) {
			model.addAttribute("message", "No products found!!");
		}
		
		products = resultList;

		products = service.sortFilterProductList(products);
		currentPage = page;
		updateProductsAndPage(model);

		return "productListPage";
	}

	@GetMapping("/compare")
	public String compareProduct(@RequestParam String productName, @RequestParam String brandname, Model model) {
		List<ProductInfo> comparedProducts = service.compareProduct(productName, brandname);

		model.addAttribute("flipkartProduct", comparedProducts.get(0));
		model.addAttribute("amazonProduct", comparedProducts.get(1));
		model.addAttribute("cromaProduct", comparedProducts.get(2));
		model.addAttribute("jiomartProduct", comparedProducts.get(3));

		model.addAttribute("productName", productName);

		return "comparePage";

	}

	@GetMapping("/addtocompare")
	public String addToCompare(@RequestParam("productUrl") String productUrl, Model model) {
		if (service.limit()) {
			service.addToCompareList(productUrl);
		}
		return "redirect:/filterProducts";
	}

	@GetMapping("/compare-products")
	public String compareSelectedProducts(Model model) {
		model.addAttribute("comparedProducts", service.getCompareList());
		return "compareProductsPage";
	}

	@PostMapping("/removeProduct")
	public String removeProduct(@RequestParam("productIndex") int productIndex) {
		service.removeFromCompareList(productIndex);
		return "redirect:/compare-products";
	}

	@GetMapping("/previousProducts")
	public String previousProducts(@RequestParam("page") int pageNumber) {
		if (pageNumber < 0) {
			return "redirect:/filterProducts";
		}

		currentPage = pageNumber - 1;
		return "redirect:/filterProducts?page=" + currentPage;
	}

	@GetMapping("/afterProducts")
	public String afterProducts(@RequestParam("page") int pageNumber) {
		if (pageNumber >= products.size()) {
			return "redirect:/filterProducts";
		}

		currentPage = pageNumber + 1;
		return "redirect:/filterProducts?page=" + currentPage;
	}

	private void updateProductsAndPage(Model model) {
		int pageSize = 40;
		int startIndex = currentPage * pageSize;
		int endIndex = Math.min(startIndex + pageSize, products.size());

		List<ProductInfo> productsPage = products.subList(startIndex, endIndex);
		model.addAttribute("products", productsPage);

		int totalPages = (int) Math.ceil((double) products.size() / pageSize);
		model.addAttribute("totalPages", totalPages);

		model.addAttribute("page", currentPage);
		model.addAttribute("category", finalCategory);
		model.addAttribute("priceRanges", service.getPriceRange());
		model.addAttribute("brands", service.getBrandName());
		model.addAttribute("platforms", service.getPlatform());
	}
}
