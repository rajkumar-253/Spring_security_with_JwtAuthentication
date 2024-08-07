package com.springJWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springJWT.model.AuthenticationResponse;
import com.springJWT.model.User;
import com.springJWT.repository.UserRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(User request) {
		User user=new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		user=userRepository.save(user);
		String token=jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}
	
	public AuthenticationResponse authenticate(User request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token=jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}

}
