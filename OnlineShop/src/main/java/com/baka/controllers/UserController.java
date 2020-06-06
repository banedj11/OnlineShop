package com.baka.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baka.models.User;
import com.baka.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	//Registration form
	@GetMapping("/registerUser")
	public String registration(Model model) {
		
		model.addAttribute("user", new User());
		return "registration";
	}
	
	//Register user
	@PostMapping("/registerUser")
	public String registrationPost(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redAtt, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "registration";
		}
		else if(userService.userExists(user.getEmail())) {
			
			model.addAttribute("userExists", true);
			return "registration";
		}
		else if(!user.getPassword().equals(user.getConfirmPassword())) {
			
			model.addAttribute("badConfPass", true);
			return "registration";
		}
		else {
			userService.createUser(user);
            redAtt.addFlashAttribute("created", true);
			
			return "redirect:/myAccount/viewAccount";
		}
			
	}
}
