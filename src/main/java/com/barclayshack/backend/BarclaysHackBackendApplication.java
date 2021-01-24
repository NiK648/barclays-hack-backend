package com.barclayshack.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BarclaysHackBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarclaysHackBackendApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/listItems").allowedOrigins("https://barclays-hack-app.herokuapp.com");
				registry.addMapping("/payment").allowedOrigins("https://barclays-hack-app.herokuapp.com");
				registry.addMapping("/details").allowedOrigins("https://barclays-hack-app.herokuapp.com");
				registry.addMapping("/register").allowedOrigins("https://barclays-hack-app.herokuapp.com");
				registry.addMapping("/login").allowedOrigins("https://barclays-hack-app.herokuapp.com");
				registry.addMapping("/update").allowedOrigins("https://barclays-hack-app.herokuapp.com");
				registry.addMapping("/orders").allowedOrigins("http://localhost:4200");
				
				/*
				 * registry.addMapping("/listItems").allowedOrigins("http://localhost:4200");
				 * registry.addMapping("/payment").allowedOrigins("http://localhost:4200");
				 * registry.addMapping("/details").allowedOrigins("http://localhost:4200");
				 * registry.addMapping("/register").allowedOrigins("http://localhost:4200");
				 * registry.addMapping("/login").allowedOrigins("http://localhost:4200");
				 * registry.addMapping("/update").allowedOrigins("http://localhost:4200");
				 * registry.addMapping("/orders").allowedOrigins("http://localhost:4200");
				 */
			}
		};
	}
	
	
	/*
	 * @Bean public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
	 * webServerFactoryCustomizer() { return factory ->
	 * factory.setContextPath("/barclays-backend"); }
	 */
	 

}
