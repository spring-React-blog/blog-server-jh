package me.jojiapp.blogserverjh.global.config;

import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.web.cors.*;

import static org.springframework.web.cors.CorsConfiguration.*;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		val corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedMethod(ALL);
		corsConfiguration.addAllowedHeader(ALL);
		corsConfiguration.addAllowedOriginPattern(ALL);
		corsConfiguration.setMaxAge(3600L);

		val source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}
