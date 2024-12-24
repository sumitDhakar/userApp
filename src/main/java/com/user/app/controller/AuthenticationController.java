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
	
	/**
	 * Endpoint to handle user signup.
	 * 
	 * @param request The SignUpRequest object containing the user's details for signup.
	 * @return ResponseEntity with the result of the signup operation and HTTP status CREATED.
	 */
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {		
		return new ResponseEntity<>(this.authService.signup(request), HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint to handle user signin (login).
	 * 
	 * @param request The SigninRequest object containing the user's email and password for login.
	 * @return ResponseEntity with the result of the login operation and HTTP status OK.
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody SigninRequest request) {
		System.err.println(request.getEmail());
		System.err.println(request.getPassword());
		return new ResponseEntity<>(this.authService.login(request), HttpStatus.OK);
	}
	
	/**
	 * Endpoint to refresh the user's access token using a refresh token.
	 * 
	 * @param request The RefreshTokenRequest object containing the refresh token.
	 * @return ResponseEntity with the refreshed access token and HTTP status OK.
	 */
	@PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
		System.err.println(request.getRefreshToken());
		return new ResponseEntity<>(this.authService.refreshAccessToken(request), HttpStatus.OK);
    }
}
