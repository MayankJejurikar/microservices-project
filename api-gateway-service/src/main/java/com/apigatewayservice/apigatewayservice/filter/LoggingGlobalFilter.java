package com.apigatewayservice.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingGlobalFilter implements GlobalFilter,Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		//log.info("LoggingFilter is running...");
		long startTime = System.currentTimeMillis();

		log.info("Request: {}", exchange.getRequest().getPath());

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			long timeTaken = System.currentTimeMillis() - startTime;

			log.info("Response status: {}", exchange.getResponse().getStatusCode());
			log.info("Time taken: {}ms", timeTaken);
		})).doOnError(error -> log.error("Error Occured: {}", error.getMessage())).then(); // Without this we will get
																							// error because then()
																							// forcefully converts
																							// Mono<Onject> to
																							// Mono<Void>
	}

	
	// By using this we can tell which GlobalFilter will be executed first
	@Override
	public int getOrder() {
		
		return -1;  // Here -1 means higher priority (runs first)
	}

}
