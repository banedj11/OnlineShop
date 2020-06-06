package com.baka.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.baka.models.Address;
import com.baka.models.Cart;
import com.baka.models.Role;
import com.baka.models.User;
import com.baka.repository.AddressRepository;
import com.baka.repository.RoleRepository;
import com.baka.repository.UserRepository;
import com.baka.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Override
	@Transactional
	public void createUser(User user) {
	    
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = roleRepo.findByName("USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
		userRepo.save(user);
	}
	
	@Override
	public void createAdmin(User user) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = roleRepo.findByName("ADMIN");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);
		userRepo.save(user);
	}
	
	@Override
	public void deleteUserById(Long id) {

        userRepo.deleteById(id);
		
	}
	
	@Override
	public void editAccount(User user) {
		
		List<Role> roles = user.getRoles();
		user.setPassword(user.getPassword());
		user.setRoles(roles);
		userRepo.save(user);
	}

	@Override
	public User findUserById(Long id) {
		
		return userRepo.findById(id).orElse(new User());
	}

	@Override
	public User findUserByEmail(String email) {
		
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> findAll() {
		
		return userRepo.findAll();
	}

	@Override
	public boolean userExists(String email) {
		
		User user = userRepo.findByEmail(email);
		if(user != null) {
			return true;
		}
		return false;
	}

    @Override
    public void setDefaultAddress(Long id, User user) {
    	
    	List<Address> addressList = addressRepo.findByUserId(user.getId());
    	
    	for(Address address : addressList) {
    		if(address.getId() == id) {
    			address.setDefaultAddress(true);
    			addressRepo.save(address);
    		}else {
    			address.setDefaultAddress(false);
    			addressRepo.save(address);
    		}
    	}
    }

	@Override
	public void changePassword(User user) {
		
		Role role = roleRepo.findByName("USER");
        List<Role> roles = new ArrayList<Role>();
        roles.add(role);
        user.setRoles(roles);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);
		userRepo.save(user);
		
	}



	

	

}
