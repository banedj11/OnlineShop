package com.baka.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baka.models.Product;
import com.baka.models.User;
import com.baka.services.ProductService;
import com.baka.services.UserService;

@Controller
public class SearchController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/searchByCategory")
	public String searchbyCategory(@RequestParam("category") String category, Model model, Principal principal) {
		
		if(principal != null) {
			
			String email = principal.getName();
			User user = userService.findUserByEmail(email);
			model.addAttribute("user", user);
		}
		String classActiveCategory = "active" + category;
		classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
		classActiveCategory = classActiveCategory.replaceAll("&", "");
		model.addAttribute(classActiveCategory, true);
		
		List<Product> products = productService.findByCategory(category);
		
		if(products.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "index";
		}
		model.addAttribute("products", products);
		return "index";
	}
	
	@PostMapping("/searchProduct")
	public String searchProduct(@RequestParam("keyword") String keyword, Model model, Principal principal) {
		
		if(principal != null) {
			
			String email = principal.getName();
			User user = userService.findUserByEmail(email);
			model.addAttribute("user", user);
		}
		
		List<Product> products = productService.blurrySearch(keyword);
		if(products.isEmpty()) {
			model.addAttribute("emptyList", true);
			return "index";
		}
		model.addAttribute("products", products);
		return "index";
	}
	
	
	
	
	
	
	
	
	
}
