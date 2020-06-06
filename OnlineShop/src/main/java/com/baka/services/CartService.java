package com.baka.services;

import com.baka.models.Cart;

public interface CartService {

	Cart updateCart(Cart cart);
    void clearCart(Cart cart);
	
}
