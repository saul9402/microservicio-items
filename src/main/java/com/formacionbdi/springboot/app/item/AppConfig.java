package com.formacionbdi.springboot.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Bean("clienteRest")
	/*
	 * Con esta anotacion de forma automatica utiliza ribbon para el balanceo de
	 * carga en el rest template. Es para restTemplate en el feing funciona sin esto
	 * 
	 * @return
	 */
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}

}
