package com.apigatewayservice.apigatewayservice.filter;

import com.apigatewayservice.apigatewayservice.ApiGatewayServiceApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.apigatewayservice.apigatewayservice.util.JwtUtil;

import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

	private Map<String, List<String>> roleAccess = new HashMap<String, List<String>>();

	@Autowired
	private JwtUtil jwtUtil;

	AuthenticationFilter() {

		roleAccess.put("USER", List.of("GET:/student/**", "GET:/course/**", "GET:/enrollment/",
				"GET:/enrollment/student/**", "POST:/enrollment/**")); // Defining
		// Allowed
		// roles
		// for
		// user

		roleAccess.put("ADMIN",
				List.of("GET:/student/**", "POST:/student/**", "PUT:/student/**", "DELETE:/student/**",
						"GET:/course/**", "POST:/course/**", "PUT:/course/**", "DELETE:/course/**",
						"GET:/enrollment/**", "DELETE:/enrollment/**")); // Defining Allowed roles for admin
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		log.info("AuthenticationFilter is running...");

		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

		// Blocking the request which contains null header or invalid token
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			log.info("Unauthorized Access!!");
			return onError(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized");
		}

		// Extracting token by removing "Bearer "
		String token = authHeader.substring(7);

		if (!jwtUtil.validateToken(token)) {
			log.info("Unauthorized Access!!");
			return onError(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized");
		}

		String username = jwtUtil.extractUsername(token);
		String role = jwtUtil.extractRole(token);
		String path = exchange.getRequest().getURI().getPath(); // Extracting path from the request
		String requestMethod = exchange.getRequest().getMethod().name();

		log.info("Request path: {}", path);
		log.info("User Role: {}", role);
		log.info("Requested Method: {}", requestMethod);

		if (!hasAccess(role, path, requestMethod)) {
			log.info("User is trying to access restricted area");
			return onError(exchange, HttpStatus.FORBIDDEN, "Access Denied");
		}

		else {

			ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("X-User", username)
					.header("X-Role", role).build();

			return chain.filter(exchange.mutate().request(modifiedRequest).build());
		}
	}

	private boolean hasAccess(String role, String path, String method) {

		// String modifiedPath = path.replaceFirst("/user", "").replaceFirst("/admin",
		// "");

		String modifiedPath = path.replaceFirst("^/(user|admin)", "");

		List<String> permissionsAllowedToRole = roleAccess.get(role);

		if (permissionsAllowedToRole == null)
			return false;

		return permissionsAllowedToRole.stream().anyMatch(permission -> {

			String[] parts = permission.split(":");

			String permittedMethod = parts[0];
			String permittedPath = parts[1].replace("/**", "");

			log.info("Method Allowed: {}", permittedMethod);
			log.info("Path Allowed: {}", permittedPath);

			return method.equals(permittedMethod) && modifiedPath.startsWith(permittedPath);
		});

	}

	private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status, String message) {

		exchange.getResponse().setStatusCode(status);
		exchange.getResponse().getHeaders().add("Content-Type", "application/json");

		String body = "{\"message\": \"" + message + "\", \"status\": " + status.value() + "}";
		byte[] bytes = body.getBytes();

		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

		return exchange.getResponse().writeWith(Mono.just(buffer));
	}

	@Override
	public int getOrder() {

		return 0; // It means low priority it means This GlobalFilter will run After Logging
					// Filter
	}

}
