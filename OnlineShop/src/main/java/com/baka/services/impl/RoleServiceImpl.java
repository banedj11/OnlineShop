package com.baka.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baka.models.Role;
import com.baka.repository.RoleRepository;
import com.baka.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public void createRole(Role role) {
		
		roleRepo.save(role);
	}
}
