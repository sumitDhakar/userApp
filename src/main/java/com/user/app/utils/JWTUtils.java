package com.user.app.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JWTUtils {

	@Value("${app.secret}")
	private String secretKey;

	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
	
	public String generateToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuer("simit dhakar")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(10)))
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).compact();
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return generateToken(claims, userName);
	}

	public Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
		} catch (MalformedJwtException e) {
			System.err.println("Invalid JWT format: " + e.getMessage());
		} catch (SignatureException e) {
			System.err.println("Invalid JWT signature: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error parsing JWT: " + e.getMessage());
		}
		return null;
	}

	public String getUserName(String token) {

		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject(); // Access the subject if claims are not null
		} else {
			System.err.println("Claims are null; unable to retrieve subject.");
			return null;
		}
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateRefreshToken(String username) {
		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuer("Sumit dhakar")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(20)))
				.signWith(SignatureAlgorithm.HS256, secretKey.getBytes()).compact();

	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
