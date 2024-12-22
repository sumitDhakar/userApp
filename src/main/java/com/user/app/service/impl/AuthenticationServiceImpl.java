package com.user.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.app.constants.AppConstants;
import com.user.app.exception.InvalidCredientials;
import com.user.app.exception.UnauthorizedException;
import com.user.app.exception.UserAlreadyPresentException;
import com.user.app.exception.UserNotFoundException;
import com.user.app.model.User;
import com.user.app.payload.RefreshTokenRequest;
import com.user.app.payload.SignUpRequest;
import com.user.app.payload.SigninRequest;
import com.user.app.repo.UserRepository;
import com.user.app.service.IAuthenticationService;
import com.user.app.utils.JWTUtils;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtils jwtUtils;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND_EMAIL + email));

		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("User"));
		return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
	}

	@Override
	public Map<String, String> signup(SignUpRequest request) {
		boolean exists = this.userRepository.existsByEmail(request.getEmail());
		if (exists)
			throw new UserAlreadyPresentException();

		User user = new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		user.setPassword(this.passwordEncoder.encode(request.getPassword()));
		this.userRepository.save(user);

		Map<String, String> response = new HashMap<>();
		response.put(AppConstants.MESSAGE, AppConstants.USER_REGISTRATION_SUCCESS);
		return response;
	}

	@Override
	public Map<String, String> login(SigninRequest request) {
		User user = this.userRepository.findByEmail(request.getEmail().trim())
				.orElseThrow(() -> new UserNotFoundException());

		if (!this.passwordEncoder.matches(request.getPassword().trim(), user.getPassword())) {
			throw new InvalidCredientials();
		}

		String refreshToken = this.jwtUtils.generateRefreshToken(user.getEmail());
		String accessToken = this.jwtUtils.generateToken(user.getEmail());
		Map<String, String> response = new HashMap<>();
		response.put(AppConstants.ACCESS_TOKEN, accessToken);
		response.put(AppConstants.REFRESH_TOKEN, refreshToken);
		return response;
	}

	@Override
	public Map<String, String> refreshAccessToken(RefreshTokenRequest request) {
		if(this.jwtUtils.validateToken(request.getRefreshToken())) {
			String username = this.jwtUtils.extractUsername(request.getRefreshToken());
			String accessToken = this.jwtUtils.generateToken(username);
			Map<String, String> response = new HashMap<>();
			response.put(AppConstants.ACCESS_TOKEN, accessToken);
			return response;
		}
		throw new UnauthorizedException();
	}

}
