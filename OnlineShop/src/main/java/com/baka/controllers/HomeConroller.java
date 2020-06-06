package com.baka.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.baka.models.Product;
import com.baka.models.User;
import com.baka.services.ProductService;
import com.baka.services.UserService;

@Controller
public class HomeConroller {

	
    @Autowired
	private ProductService productService;
	
    @Autowired
    private UserService userService;
    
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		
		if(principal != null) {
			User user = userService.findUserByEmail(principal.getName());
			String name = user.getFirstName() + " " + user.getLastName();
			model.addAttribute("name", name);
			model.addAttribute("authenticated", true);
		}
		
		List<Product> products = productService.findAllActive();
		model.addAttribute("products", products);
		return "index";
	}
	
	@GetMapping("/productDetails")
	public String productDetails(@PathParam("id") Long id, Model model) {
		
		Product product = productService.findById(id).orElse(new Product());
		model.addAttribute("product", product);
		
		List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);
		return "productDetails";
	}
}
