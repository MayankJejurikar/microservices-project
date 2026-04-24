package com.apigatewayservice.apigatewayservice.util;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	// Generating token
	public String generateToken(String userName, String role) {

		long expirationTime = 1000 * 60 * 30; // Token will expire in 10 minute

		// Jwts.builder().compact() this line creates empty token
		// Jwts.builder().setSubject(userName).compact() this line will add username in
		// to payloads

		return Jwts.builder().setSubject(userName).claim("role", role)
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256).compact();
	}

	// Validating token which is sent by user/admin in headers
	public boolean validateToken(String token) {

		try {
			Jwts.parserBuilder() // Start validation of JWT token
					.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())) // Use my secret key to verify token
					.build() // my validator is ready
					.parseClaimsJws(token); // Now verify token based on given details
			return true;
		} catch (ExpiredJwtException e) {

			log.error("Token is expired!! please generate new token {}", e.getMessage());
			return false;
		} catch (SignatureException e) {

			log.error("Token is tampered!! {}", e.getMessage());

			return false;
		} catch (Exception e) {

			log.error("Invalid Token ", e);
			return false;
		}

	}

	// Extracting user name from token
	public String extractUsername(String token) {

		String username = extractClaim(token, Claims::getSubject);

		log.info("Username: {}", username);

		return username;
	}

	// Extracting Role from token
	public String extractRole(String token) {

		String role = extractClaim(token, c -> c.get("role", String.class));

		log.info("Role: {}", role);

		return role;
	}

	// Extracting Expiration time of the token
	public Date extractExpiration(String token) {

		Date expirationTime = extractClaim(token, Claims::getExpiration);

		return expirationTime;
	}

	// Extracting username,role and expiration using below function
	public <T> T extractClaim(String token, Function<Claims, T> resolver) {

		final Claims claims = extraAllClaims(token);

		return resolver.apply(claims);
	}

	// Centralised parsing and extracting claims
	public Claims extraAllClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())).build()
				.parseClaimsJws(token).getBody();
	}

}
