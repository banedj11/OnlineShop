package com.baka.services;

import java.util.List;
import java.util.Optional;

import com.baka.models.Address;
import com.baka.models.User;

public interface AddressService {

	void createAddress(Address address, User user);
	Optional<Address> findOne(Long id);
	List<Address> findAll();
	List<Address> findByUserId(Long id);
	void deleteById(Long id);
	
}
