package com.abhijit.restfulmicroservice.hello;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

	@Autowired
	MessageSource messageSource;

	@GetMapping("/hello")
	public String i18nMessage(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		return messageSource.getMessage("good.morning.message", null,"Good Morning", locale);
	}
	
	@GetMapping("/hello-goodapproach")
	public String i18nMessageBestApproach() {
		return messageSource.getMessage("good.morning.message", null,"Good Morning", LocaleContextHolder.getLocale());
	}


}
