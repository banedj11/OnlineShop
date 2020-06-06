package com.baka.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baka.models.Cart;
import com.baka.models.CartItem;
import com.baka.repository.CartRepository;
import com.baka.services.CartItemService;
import com.baka.services.CartService;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Override
	public Cart updateCart(Cart cart) {
		
		Double cartTotal = 0.0;
		List<CartItem> cartItems = cartItemService.findByCart(cart);
		
		for(CartItem cartItem : cartItems) {
			
			if(cartItem.getProduct().getInStockNumber() > 0) {
				
				cartItemService.updateCartItem(cartItem);
				cartTotal = cartTotal + (cartItem.getTotalPrice());
			}
		}
		cart.setGrandTotal(cartTotal);
		cartRepo.save(cart);
		
		return cart;
	}

	

	@Override
	public void clearCart(Cart cart) {
		
		List<CartItem> cartItems = cartItemService.findByCart(cart);
		
		for(CartItem cartItem : cartItems) {
			cartItem.setCart(null);
			cartItemService.saveCartItem(cartItem);
		}
		cart.setGrandTotal(0.0);
		cartRepo.save(cart);
	}

}
