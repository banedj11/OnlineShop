package com.baka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baka.models.Cart;
import com.baka.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	List<CartItem> findByCart(Cart cart);
}
