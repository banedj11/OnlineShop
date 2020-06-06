package com.baka.services;

import java.util.List;
import java.util.Optional;

import com.baka.models.Product;

public interface ProductService {

	Optional<Product> findById(Long id);
	List<Product> findAll();
	void create(Product product);
	void deleteById(Long id);
	List<Product> findByCategory(String category);
	List<Product> findAllActive();
	List<Product> blurrySearch(String title);
}
