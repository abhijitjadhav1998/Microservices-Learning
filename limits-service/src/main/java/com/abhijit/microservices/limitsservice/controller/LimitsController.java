package com.abhijit.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhijit.microservices.limitsservice.bean.Limits;
import com.abhijit.microservices.limitsservice.configuration.Configuration;

@RestController
public class LimitsController {
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public Limits retrieveLimts() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}
}
