package com.abhijit.microservices.currencyconversionmicroservice.Controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.abhijit.microservices.currencyconversionmicroservice.Entity.CurrencyConversion;
import com.abhijit.microservices.currencyconversionmicroservice.proxy.CurrencyConversionProxy;

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyConversionProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		CurrencyConversion currencyConversion = responseEntity.getBody();
		return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(),
				currencyConversion.getTo(), currencyConversion.getConversionMultiple(), quantity,
				quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " rest template");

	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		CurrencyConversion currencyConversion = proxy.retriveExchangeValue(from, to);
		return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(),
				currencyConversion.getTo(), currencyConversion.getConversionMultiple(), quantity,
				quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " feign");

	}

}
