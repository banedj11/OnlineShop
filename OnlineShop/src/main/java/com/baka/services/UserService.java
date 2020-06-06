package com.baka.services;

import java.util.List;
import java.util.Optional;


import com.baka.models.User;

public interface UserService {

	void createUser(User user);
	void createAdmin(User user);
	void deleteUserById(Long id);
	User findUserById(Long id);
	User findUserByEmail(String email);
	List<User> findAll();
	boolean userExists(String email);
	void editAccount(User user);
	void setDefaultAddress(Long id, User user);
	void changePassword(User user);
}
