package me.jojiapp.blogserverjh.global.config;

import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.factory.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.web.cors.*;

import static org.springframework.web.cors.CorsConfiguration.*;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

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

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
				.antMatchers("/favicon.ico", "/docs/**");
	}

	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
		return http
				.formLogin()
				.disable()
				.csrf()
				.disable()
				.cors()
				.configurationSource(corsConfigurationSource())
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.headers()
				.frameOptions()
				.disable()
				.and()
				.antMatcher("/**")
				.authorizeRequests()
				.anyRequest().permitAll()
				.and()
				.build();
	}
}
