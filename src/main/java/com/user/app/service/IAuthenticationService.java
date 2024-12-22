package com.user.app.service;

import java.util.Map;

import com.user.app.payload.RefreshTokenRequest;
import com.user.app.payload.SignUpRequest;
import com.user.app.payload.SigninRequest;

public interface IAuthenticationService {

	Map<String, String> signup(SignUpRequest request);

	Map<String,String> login(SigninRequest request);

	Map<String, String> refreshAccessToken(RefreshTokenRequest request);

}
