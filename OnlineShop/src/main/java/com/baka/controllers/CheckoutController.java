package com.baka.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baka.configuration.MailConstructor;
import com.baka.models.Address;
import com.baka.models.Cart;
import com.baka.models.CartItem;
import com.baka.models.Order;
import com.baka.models.User;
import com.baka.services.AddressService;
import com.baka.services.CartItemService;
import com.baka.services.CartService;
import com.baka.services.OrderService;
import com.baka.services.UserService;

@Controller
public class CheckoutController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@GetMapping("/checkOut")
	public String checkOut(@RequestParam(value = "id", required = false) Long cartId, 
			               Principal principal, RedirectAttributes redirAttr, Model model) {
		
		User user = userService.findUserByEmail(principal.getName());
		Address defaultAddress = new Address();
		if(cartId != user.getCart().getId()) {
			return "badRequestPage";
		}
		
		List<CartItem> cartItems = cartItemService.findByCart(user.getCart());
		
		if(cartItems.size() == 0) {
			redirAttr.addFlashAttribute("emptyCart", true);
			return "redirect:/shoppingCart/cart";
		}
		
		for(CartItem cartItem : cartItems) {
			if(cartItem.getProduct().getInStockNumber() < cartItem.getQuantity()) {
				redirAttr.addFlashAttribute("notEnough", true);
				return "redirect:/shoppingCart/cart";
			}
		}
		
		List<Address> addreses = addressService.findByUserId(user.getId());
		for(Address address : addreses) {
			System.out.println(address);
		}
		model.addAttribute("addreses", addreses);
		
		if(addreses.size() == 0) {
			model.addAttribute("emptyAddressList", true);
		}
		else {
			model.addAttribute("emptyAddressList", false);
		}
		
		Cart cart = user.getCart();
		
		for(Address address : addreses) {
			if(address.isDefaultAddress()) {
				defaultAddress = addressService.findOne(address.getId()).orElse(new Address());
			}
		}
		
		model.addAttribute("address", defaultAddress);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("cart", cart);
		model.addAttribute("classActiveShipping", true);
		
		return "checkOut";
 	}
	
	//Checkout post method
	@PostMapping("/checkOut")
	public String checkOutPost(@ModelAttribute("address") Address newAddress,
			                   @RequestParam("street") String street, @RequestParam("state") String state, @RequestParam("city") String city, @RequestParam("zipCode") String zipCode,
			                   @RequestParam("addressId") Long addressId, 
			                   @RequestParam("orderMethod") String orderMethod, Principal principal, Model model) {
		
		Cart cart = (userService.findUserByEmail(principal.getName()).getCart());
		List<CartItem> cartItems = cartItemService.findByCart(cart);
		model.addAttribute("cartItems", cartItems);
		User user = userService.findUserByEmail(principal.getName());
		
		Order order = orderService.createOrder(user, cart, street, city, state, zipCode, orderMethod);
		mailSender.send(mailConstructor.constructOrderInformationEmail(user, order, Locale.ENGLISH));
		cartService.clearCart(cart);
		
		return "orderSubmitted";
		
		
	}
	
	//Setting address where user wants his product to be delivered at
	@GetMapping("/setShippingAddress")
	public String setShippingAddress(@RequestParam("userAddressId") Long addressId, Model model, Principal principal) {
		
		User user = userService.findUserByEmail(principal.getName());
		Cart cart = user.getCart();
		Address address = addressService.findOne(addressId).orElse(new Address());
		List<Address> addreses = addressService.findByUserId(user.getId());
		model.addAttribute("addreses", addreses);
		
		if(addreses.size() == 0) {
			model.addAttribute("emptyAddressList", true);
		}
		else {
			model.addAttribute("emptyAddressList", false);
		}
		List<CartItem> cartItems = cartItemService.findByCart(user.getCart());
		System.out.println(address);
		model.addAttribute("address", address);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("cart", cart);
		model.addAttribute("classActiveShipping", true);
		return "checkOut";
	}
	
	
	
	
	
	
}
