package com.baka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baka.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	
}
