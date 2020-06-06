package com.baka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baka.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByCategory(String category);
    List<Product> findByNameContaining(String name);
	
}
