package com.abhijit.microservices.currencyexchangemicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhijit.microservices.currencyexchangemicroservice.Bean.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {

	CurrencyExchange findByFromAndTo(String from, String to);

}
