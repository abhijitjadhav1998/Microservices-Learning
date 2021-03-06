package com.abhijit.microservices.currencyexchangemicroservice.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	@Retry(name = "sample-api",fallbackMethod = "hardcodedResponse")
	public String sampleApi() {
		logger.info("Sample API call recieved");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
		return forEntity.getBody();
	}
	
	@GetMapping("/sample-api-circuit")
	@CircuitBreaker(name = "default",fallbackMethod = "hardcodedResponse")
	public String sampleApiCircuitBreaker() {
		logger.info("Sample API call recieved");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
		return forEntity.getBody();
	}
	@GetMapping("/sample-api-ratelimiter")
	@RateLimiter(name = "default") // in 10 second allow 1000 calls to the sample api
	public String sampleApiRateLimiter() {
		return "rate limiter";
	}
	
	@GetMapping("/sample-api-bulkhead")
	@Bulkhead(name = "default") // in 10 second allow 1000 calls to the sample api
	public String sampleApiBulkhead() {
		return "BulK limiter";
	}
	
	private String hardcodedResponse(Exception exception) {
		return "Microservice http://localhost:8080/some-dummy-url is down";
	}
}
