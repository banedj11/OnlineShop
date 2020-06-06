package com.baka.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baka.models.Address;
import com.baka.models.User;
import com.baka.repository.AddressRepository;
import com.baka.services.AddressService;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressRepository addressRepo;

	@Override
	public void createAddress(Address address, User user) {
		
		address.setDefaultAddress(true);
		address.setUser(user);
		addressRepo.save(address);
	}

	@Override
	public Optional<Address> findOne(Long id) {
		
		return addressRepo.findById(id);
	}

	@Override
	public List<Address> findAll() {
		
		return addressRepo.findAll();
	}
	
	@Override
	public List<Address> findByUserId(Long id){
		
		return addressRepo.findByUserId(id);
	}

	@Override
	public void deleteById(Long id) {
		
		addressRepo.deleteById(id);
	}

	

	

}
