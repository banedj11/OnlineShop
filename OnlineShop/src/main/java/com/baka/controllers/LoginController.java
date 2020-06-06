package com.baka.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.baka.models.User;
import com.baka.services.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	public void setName(Principal principal, Model model) {
		
		String email = principal.getName();
		User user = userService.findUserByEmail(email);
		String name = user.getFirstName() + " " + user.getLastName();
		model.addAttribute("name", name);
	}
}
