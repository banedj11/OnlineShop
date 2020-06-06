package com.baka.services;

import java.util.List;
import java.util.Optional;

import com.baka.models.Cart;
import com.baka.models.CartItem;
import com.baka.models.Product;
import com.baka.models.User;

public interface CartItemService {

  	List<CartItem> findByCart(Cart cart);
  	CartItem updateCartItem(CartItem cartItem);
	CartItem addProductToCartItem(Product product, User user, int quantity);
	void deleteCartItem(Long id);
	CartItem saveCartItem(CartItem cartItem);
	void updateQuantity(CartItem cartItem);
	Optional<CartItem> findById(Long id);
}
