package com.dns.rpslottolandbackend.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Not safe for production.
// Used only because this is not a real program.
// Same reason is not https
// Same reason is not user authentication/authorization
@Configuration
@EnableAutoConfiguration
public class CORSConfiguration {

	@Bean
	WebMvcConfigurer allowAllCORS() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

}
