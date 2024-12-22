package com.user.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.app.payload.RefreshTokenRequest;
import com.user.app.payload.SignInResponse;
import com.user.app.payload.SignUpRequest;
import com.user.app.payload.SigninRequest;
import com.user.app.service.IAuthenticationService;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthenticationController {

	

	
	@Autowired
	private IAuthenticationService authService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {		
		return new ResponseEntity<>(this.authService.signup(request), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody SigninRequest request) {
		return new ResponseEntity<>(this.authService.login(request), HttpStatus.OK);
	}
	
	
	@PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
		return new ResponseEntity<>(this.authService.refreshAccessToken(request), HttpStatus.OK);
    }
}
