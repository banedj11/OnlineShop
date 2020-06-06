package com.baka.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baka.models.Product;
import com.baka.repository.ProductRepository;
import com.baka.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public Optional<Product> findById(Long id) {
		
		return productRepo.findById(id);
	}

	@Override
	public List<Product> findAll() {
		
		return productRepo.findAll();
	}

	@Override
	public List<Product> findAllActive() {
		
		List<Product> allProducts = productRepo.findAll();
		List<Product> activeProducts = new ArrayList<>();
		
		for(Product product : allProducts) {
			
			if(product.isActive()) {
				activeProducts.add(product);
			}
		}
		return activeProducts;
	}
	
	@Override
	public void create(Product product) {
		
		productRepo.save(product);
	}

	@Override
	public void deleteById(Long id) {
		
        productRepo.deleteById(id);
	}

	@Override
	public List<Product> findByCategory(String category) {
		
		List<Product> products = productRepo.findByCategory(category);
		
		List<Product> activeProducts = new ArrayList<>();
		
		for(Product product : products) {
			if(product.isActive()) {
				activeProducts.add(product);
			}
		}
		return activeProducts;
	}

	@Override
	public List<Product> blurrySearch(String title) {
		
        List<Product> products = productRepo.findByNameContaining(title);
		
		List<Product> activeProducts = new ArrayList<>();
		
		for(Product product : products) {
			if(product.isActive()) {
				activeProducts.add(product);
			}
		}
		return activeProducts;
	}

}
