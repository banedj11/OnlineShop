package com.baka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baka.models.Address;
import com.baka.models.Role;
import com.baka.models.User;
import com.baka.services.AddressService;
import com.baka.services.RoleService;
import com.baka.services.UserService;

@SpringBootApplication
public class OnlineShopApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(OnlineShopApplication.class, args);
	}
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AddressService addressService;
	
	@Override
	public void run(String... args) throws Exception {
		
		Role roleAdmin = new Role(1, "ADMIN");
		Role roleUser = new Role(2, "USER");
		
		roleService.createRole(roleAdmin);
		roleService.createRole(roleUser);
		
		//Admin
		User admin = new User();
		admin.setId((long) 1);
		admin.setFirstName("Admin");
		admin.setLastName("Adminic");
		admin.setEmail("admin@live.com");
		admin.setPassword("admin");
		admin.setConfirmPassword("admin");
		admin.setMobile("0695547898");
	    userService.createAdmin(admin);
		
	    //User
		User user = new User();
		user.setId((long) 2);
		user.setFirstName("User");
		user.setLastName("Useric");
		user.setEmail("user@live.com");
		user.setPassword("user");
		user.setConfirmPassword("user");
		user.setMobile("0695547898");
		userService.createUser(user);
		
		Address address = new Address();
		address.setStreet("High Street 123");
		address.setCity("Belgrade");
		address.setState("Serbia");
		address.setZipCode("101801");
		address.setDefaultAddress(true);
		addressService.createAddress(address, user);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
