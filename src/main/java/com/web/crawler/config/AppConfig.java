package com.web.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {
	
	@Bean
	public WebClient createWebClient() {
		WebClient webClient = WebClient.builder()
				.exchangeStrategies(ExchangeStrategies.builder().codecs(this::acceptedCodecs).build())
				.build();
		return webClient;
	}
	
	private void acceptedCodecs(ClientCodecConfigurer configurer) {
		//configurer.defaultCodecs().
		configurer.defaultCodecs().maxInMemorySize(16*1024*1024);
//		clientCodecConfigurer.customCodecs().register(new Jackson2JsonEncoder(new ObjectMapper(), 
//				MediaType.TEXT_HTML));
//		clientCodecConfigurer.customCodecs().register(new Jackson2JsonDecoder(new ObjectMapper(), 
//				MediaType.TEXT_HTML));
	    //clientCodecConfigurer.customCodecs().encoder();
	    //clientCodecConfigurer.customCodecs().decoder(new Jackson2JsonDecoder(new ObjectMapper(), TEXT_HTML));
	}

}
