package com.baka.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baka.models.Cart;
import com.baka.models.CartItem;
import com.baka.models.Product;
import com.baka.models.User;
import com.baka.services.CartItemService;
import com.baka.services.CartService;
import com.baka.services.ProductService;
import com.baka.services.UserService;

@Controller
@RequestMapping("/shoppingCart")
public class CartController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private ProductService productService;
	
	//Show cart
	@GetMapping("/cart")
	public String cart(Model model, Principal principal) {
		
		User user = userService.findUserByEmail(principal.getName());
		Cart cart = user.getCart();
		
		List<CartItem> cartItems = cartItemService.findByCart(cart);
		if(cartItems.isEmpty()) {
			
			model.addAttribute("emptyCart", true);
		}
		cartService.updateCart(cart);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("cart", cart);
		return "shoppingCart";
	}
	
	//Add product to cart item
	@PostMapping("/addProduct")
	public String addProduct(@ModelAttribute("product") Product product, @ModelAttribute("qty") String quantity, Principal principal, RedirectAttributes redirAtt) {
		
		User user = userService.findUserByEmail(principal.getName());
		product = productService.findById(product.getId()).orElse(new Product());
		if(Integer.parseInt(quantity) > product.getInStockNumber()) {
			redirAtt.addFlashAttribute("notEnoughInStock", true);
			return "redirect:/productDetails?id=" + product.getId();
		}
		cartItemService.addProductToCartItem(product, user, Integer.parseInt(quantity));
		redirAtt.addFlashAttribute("productAddSuccess", true);
		return "redirect:/productDetails?id=" + product.getId();
	}
	
	//delete cart item from cart
	@GetMapping("/removeCartItem")
	public String removeCartItem(@RequestParam Long id, RedirectAttributes redirAtt) {
		
		cartItemService.deleteCartItem(id);
		redirAtt.addFlashAttribute("deleted", true);
		return "redirect:/shoppingCart/cart";
	}
	
	@RequestMapping(value = "/updateQuantity", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateQuantity(@ModelAttribute CartItem cartItem, Model model) {
    	
		CartItem currentCartItem = cartItemService.findById(cartItem.getId()).orElse(new CartItem());
		currentCartItem.setQuantity(cartItem.getQuantity());
		cartItemService.updateCartItem(cartItem);
		model.addAttribute("qtyUpdated", true);
    	return "shoppingCart";
    }
	
	
	
}
