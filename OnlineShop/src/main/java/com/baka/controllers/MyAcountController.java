package com.baka.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baka.models.Address;
import com.baka.models.CartItem;
import com.baka.models.Order;
import com.baka.models.Role;
import com.baka.models.User;
import com.baka.services.AddressService;
import com.baka.services.OrderService;
import com.baka.services.UserService;

@Controller
@RequestMapping("/myAccount")
public class MyAcountController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private OrderService orderService;
	
	
	//view my account page
	@GetMapping("/viewAccount")
	public String myAcount(Model model, Principal principal) {
		
		String email = principal.getName();
		User user = userService.findUserByEmail(email);
		String name = user.getFirstName() + " " + user.getLastName();
		List<Address> addresses = addressService.findByUserId(user.getId());
		List<Order> userOrders = user.getOrders();
		
		if(userOrders.isEmpty()) {
			model.addAttribute("emptyOrders", true);
		}
		if(addresses.isEmpty()) {
			model.addAttribute("emptyList", true);
		}else {
			model.addAttribute("emptyList", false);
		}
		for(Address defaultAddress : addresses) {
			if(defaultAddress.isDefaultAddress()) {
				model.addAttribute("defaultAddress", defaultAddress);
			}
		}
		model.addAttribute("addresses", addresses);
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("user", user);
		model.addAttribute("orders", userOrders);
		return "myAccount";
	}
	
	//edit account get method
	@GetMapping("/editAccount")
	public String editAcount(@RequestParam Long id, Model model) {
		
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		return "editAccount";
	}
	
	//edit account post method
	@PostMapping("/editAccount")
	public String editAccountPost(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redirAtt, Model model){
		
		User currentUser = userService.findUserById(user.getId());
		
		if(bindingResult.hasErrors()) {
			return "editAccount";
		}
		if(userService.userExists(user.getEmail())){
			if(userService.findUserByEmail(user.getEmail()).getId() != currentUser.getId()) {
				model.addAttribute("userExists", true);
				return "editAccount";
			}
			
		}
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setEmail(user.getEmail());
		currentUser.setMobile(user.getMobile());
		userService.editAccount(currentUser);
		redirAtt.addFlashAttribute("edited", true);
		return "redirect:/myAccount/viewAccount";
	}
	//Address list
	@GetMapping("/addressList")
	public String addressList(Model model, Principal principal) {
		
		User user = userService.findUserByEmail(principal.getName());
		List<Address> addresses =  addressService.findByUserId(user.getId());
		model.addAttribute("addresses", addresses);
		return "addressList";
	}
	
	//Create Address
	@GetMapping("/createAddress")
	public String createAddress(Model model) {
		
         model.addAttribute("address", new Address());
         return "createAddress";
	}
	//Create address post method
	@PostMapping("/createAddress")
	public String createAddressPost(@ModelAttribute("address") @Valid Address address, BindingResult bindingResult, RedirectAttributes redirAtt, Model model, Principal principal) {
		
		
        if(bindingResult.hasErrors()) {
			
			return "createAddress";
		}
		String email = principal.getName();
		User user = userService.findUserByEmail(email);
		addressService.createAddress(address, user);
		redirAtt.addFlashAttribute("addressCreated", true);
		
		return "redirect:/myAccount/addressList";
	}
	
	//Update Address get method
	@GetMapping("/updateAddress")
	public String updateAddress(@RequestParam Long id, Model model) {
		
		Address address = addressService.findOne(id).orElse(new Address());
		model.addAttribute("address", address);
		return "updateAddress";
	}
	
	//Update address post method
	@PostMapping("/updateAddress")
	public String updateAddressPost(@ModelAttribute("address") @Valid Address address, BindingResult bindingResult, RedirectAttributes redirAtt, Model model, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			
			return "updateAddress";
		}
		
		String email = principal.getName();
		User user = userService.findUserByEmail(email);
		addressService.createAddress(address, user);
		redirAtt.addFlashAttribute("addressUpdated", true);
		
		return "redirect:/myAccount/addressList";
	}
	
	//Set default address
	@PostMapping("/setDefaultAddress")
	public String setDefaultAddress(@ModelAttribute("defaultAddressId") Long id, Model model, Principal principal, RedirectAttributes redirAtt) {
		
		User user = userService.findUserByEmail(principal.getName());
		userService.setDefaultAddress(id, user);
		redirAtt.addFlashAttribute("changed", true);
		return "redirect:/myAccount/viewAccount";
	}
	
	//Delete address
	@GetMapping("/deleteAddress")
	public String deleteAddress(@RequestParam Long id, RedirectAttributes redirAtt) {
		
		addressService.deleteById(id);
		redirAtt.addFlashAttribute("deleted", true);
		return "redirect:/myAccount/addressList";
	}
	
	//View CartItems
	@GetMapping("/viewOrderItems")
	public String viewCartItems(@RequestParam("id") Long orderId, Model model) {
		
		Order order = orderService.findOne(orderId).orElse(new Order());
		List<CartItem> cartItems = order.getCartItems();
		model.addAttribute("cartItems", cartItems);
		
		return "orderItems";
	}
	
	//Change password page
	@GetMapping("/changePassword")
	public String changePasswordGet() {
		
		return "changePassword";
	}
	
	//Change password post method
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("password") String newPassword, 
			                     @RequestParam("currentPassword") String currentPassword, 
			                     @RequestParam("confirmPassword") String confirmPassword, 
			                     Principal principal, Model model, RedirectAttributes redirAtt) {
		
		User user = userService.findUserByEmail(principal.getName());
		System.out.println(user);
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		String oldPassword = user.getPassword();
		if(!passEncoder.matches(currentPassword, oldPassword)) {
			model.addAttribute("currPassDontMatch", true);
			return "changePassword";
		}
		else if(!newPassword.equals(confirmPassword)) {
			model.addAttribute("passDontMatch", true);
			return "changePassword";
		}
		else if(newPassword.length() < 4) {
			model.addAttribute("minimum", true);
			return "changePassword";
		}
		else {
			user.setId(user.getId());
			user.setFirstName(user.getFirstName());
			user.setLastName(user.getLastName());
			user.setEmail(user.getEmail());
			user.setMobile(user.getMobile());
			user.setPassword(passEncoder.encode(newPassword));
			userService.changePassword(user);
			redirAtt.addFlashAttribute("passChanged", true);
			
			return "redirect:/myAccount/viewAccount";
		}
		
	}
	
	
}
