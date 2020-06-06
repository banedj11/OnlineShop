package com.baka.services.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baka.models.Address;
import com.baka.models.Cart;
import com.baka.models.CartItem;
import com.baka.models.Order;
import com.baka.models.Product;
import com.baka.models.User;
import com.baka.repository.OrderRepository;
import com.baka.services.CartItemService;
import com.baka.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Override
	public synchronized Order createOrder(User user, Cart cart, String street, String city, String state, String zipCode, String orderMethod) {
		
		Order order = new Order();
		
		order.setUser(user);
		order.setStreet(street);
		order.setCity(city);
		order.setState(state);
		order.setZipCode(zipCode);
		order.setOrderStatus("Created");
		order.setOrderTotal(cart.getGrandTotal());
		order.setOrderMethod(orderMethod);
		List<CartItem> cartItems = cartItemService.findByCart(cart);
		
		for(CartItem cartItem : cartItems) {
			
			Product product = cartItem.getProduct();
			cartItem.setOrder(order);
			product.setInStockNumber(product.getInStockNumber() - cartItem.getQuantity());
		}
		order.setCartItems(cartItems);
		order.setOrderDate(Calendar.getInstance().getTime());
		order = orderRepo.save(order);
		
		return order;
		}

	@Override
	public Optional<Order> findOne(Long id) {
		
		return orderRepo.findById(id);
	}

	
}
