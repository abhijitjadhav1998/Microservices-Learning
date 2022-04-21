package com.abhijit.microservices.currencyconversionmicroservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.abhijit.microservices.currencyconversionmicroservice.Entity.CurrencyConversion;

@FeignClient(name = "currency-exchange", url = "http://localhost:8000/")
public interface CurrencyConversionProxy {
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retriveExchangeValue(@PathVariable String from,@PathVariable String to);

}
