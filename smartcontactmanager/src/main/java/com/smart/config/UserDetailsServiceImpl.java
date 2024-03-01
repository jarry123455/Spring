package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.UserRepository;
import com.smart.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database
		
		User user = repository.getUserByUserName(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Could Not Found Exception");
		}
		
		CustomerUserDetails customerUserDetails = new CustomerUserDetails(user);
		return customerUserDetails;
	}

}
