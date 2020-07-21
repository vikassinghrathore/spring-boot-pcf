package com.microservices.currencyconversionservice.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

////http://localhost:8000
//@FeignClient(name = "currency-exchange-service", url="${CURRENCY_EXCHANGE_URI:http://localhost:8000}")
@FeignClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

	///currency-exchange/from/EUR/to/INR
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from,
			@PathVariable("to") String to);
}