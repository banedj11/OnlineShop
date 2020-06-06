package com.baka.services;


import java.util.Optional;

import com.baka.models.Address;
import com.baka.models.Cart;
import com.baka.models.Order;
import com.baka.models.User;

public interface OrderService {

	Order createOrder(User user, Cart cart, String street, String city, String state, String zipCode, String orderMethod);
	Optional<Order> findOne(Long id);
	
}
