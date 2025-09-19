package com.shopsense.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shopsense.repository.AdminRepository;
import com.shopsense.repository.CustomerRepository;
import com.shopsense.repository.SellerRepository;

@Configuration
public class ApplicationConfig {
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	SellerRepository sellerRepository;
	
	@Bean
	AuthProvider authProvider() {
		AuthProvider authProvider = new AuthProvider(userDetailsService());
		return authProvider;
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				UserDetails u = null;
				u = adminRepository.findByEmail(username).orElse(null);
				if(u == null) {
					u = customerRepository.findByEmail(username).orElse(null);
				}
				if(u == null) {
					u = sellerRepository.findByEmail(username).orElse(null);
				}
				return u;
			}
		};
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
