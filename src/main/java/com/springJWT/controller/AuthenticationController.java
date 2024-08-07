package com.springJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springJWT.model.AuthenticationResponse;
import com.springJWT.model.User;
import com.springJWT.service.AuthenticationService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
		return ResponseEntity.ok(authenticationService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
	

	@GetMapping("/demo")
	public ResponseEntity<String> demoSecuredUrl(){
		return ResponseEntity.ok("I am from Secured Url");
	}
	

	@GetMapping("/admin_only")
	public ResponseEntity<String> adminOnly(){
		return ResponseEntity.ok("I am Only for admin");
	}

}
