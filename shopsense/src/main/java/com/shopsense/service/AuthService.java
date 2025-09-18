package com.shopsense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopsense.repository.AdminRepository;
import com.shopsense.repository.CustomerRepository;
import com.shopsense.repository.SellerRepository;
import com.shopsense.dto.AuthRequest;
import com.shopsense.dto.AuthResponse;
import com.shopsense.security.JwtService;

@Service
public class AuthService {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	SellerRepository sellerRepository;

	public AuthResponse login(AuthRequest a) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword()));
		var user = adminRepository.findByEmail(a.getEmail())
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		var token = jwtService.generateToken(user);
		user.setPassword(null);
		return AuthResponse.builder().status("success").token(token).user(user).build();
	}
	
	public AuthResponse customerLogin(AuthRequest a) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword()));
		var user = customerRepository.findByEmailAndStatus(a.getEmail(), "Active")
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		var token = jwtService.generateToken(user);
		user.setPassword(null);
		return AuthResponse.builder().status("success").token(token).user(user).build();
	}
	
	public AuthResponse sellerLogin(AuthRequest a) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword()));
		var user = sellerRepository.findByEmailAndStatus(a.getEmail(), "Active")
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		var token = jwtService.generateToken(user);
		user.setPassword(null);
		return AuthResponse.builder().status("success").token(token).user(user).build();
	}
}
