package com.springJWT.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springJWT.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String secretKey = "9fac15a69c5d1f0e052e61334ecd4daf59e01e1b540cbd84a4819209c00da18f";

	public String generateToken(User user) {
		String token = Jwts
				.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
				.signWith(getSigninKey())
				.compact();
		return token;
	}

	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {
		Claims claims = Jwts
				.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims;
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isValid(String token, UserDetails user) {
		return extractUsername(token).equals(user.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
