package com.microservices.currencyconversionservice.resource;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.currencyconversionservice.util.environment.InstanceInformationService;

@RestController
@RefreshScope
public class CurrencyConversionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);

	@Autowired
	private InstanceInformationService instanceInformationService;

	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@Value("${CONVERSION_PROFIT_PERCENTAGE:5}")
	private int conversionProfitPercentage;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		LOGGER.info("Received Request to convert from {} {} to {}. Profit - {} ", quantity, from, to,conversionProfitPercentage);

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

		BigDecimal convertedValue = quantity.multiply(response.getConversionMultiple());

		String conversionEnvironmentInfo = instanceInformationService.retrieveInstanceInfo();

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				convertedValue, response.getExchangeEnvironmentInfo(), conversionEnvironmentInfo);
	}
}
