package com.baka.services.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.PreRemove;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baka.models.Cart;
import com.baka.models.CartItem;
import com.baka.models.Product;
import com.baka.models.User;
import com.baka.repository.CartItemRepository;
import com.baka.services.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService{

	@Autowired
	private CartItemRepository cartItemRepo;
	
	@Override
	public List<CartItem> findByCart(Cart cart) {
		
		return cartItemRepo.findByCart(cart);
	}

	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		
		Double total = (cartItem.getProduct().getPrice()) * cartItem.getQuantity();
		cartItem.setTotalPrice(total);
		cartItemRepo.save(cartItem);
		return cartItem;
	}
	
	@Override
	public CartItem addProductToCartItem(Product product, User user, int quantity) {
		
		List<CartItem> cartItems = cartItemRepo.findByCart(user.getCart());
		for(CartItem cartItem : cartItems) {
			if(product.getId() == cartItem.getProduct().getId()) {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				cartItem.setTotalPrice(product.getPrice() * quantity);
				cartItemRepo.save(cartItem);
				
				return cartItem;
			}
		}
		CartItem cartItem = new CartItem();
		cartItem.setCart(user.getCart());
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		cartItem.setTotalPrice(product.getPrice() * quantity);
		cartItem = cartItemRepo.save(cartItem);
		
		return cartItem;
	}

	@Override
	public void deleteCartItem(Long id) {
		
		cartItemRepo.deleteById(id);
	}

	@Override
	public CartItem saveCartItem(CartItem cartItem) {
		
		return cartItemRepo.save(cartItem);
	}
	
	@Override
	public Optional<CartItem> findById(Long id) {
		
		return cartItemRepo.findById(id);
	}

	@Override
	@Transactional
	public void updateQuantity(CartItem cartItem) {
		
		CartItem curentCartItem = cartItemRepo.findById(cartItem.getId()).orElse(new CartItem());
		cartItem.setTotalPrice(curentCartItem.getTotalPrice());
		cartItem.setProduct(curentCartItem.getProduct());
		
		cartItemRepo.save(curentCartItem);
	}

	
}
