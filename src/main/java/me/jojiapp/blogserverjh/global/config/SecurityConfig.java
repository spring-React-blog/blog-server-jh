package me.jojiapp.blogserverjh.global.config;

import com.fasterxml.jackson.databind.*;
import lombok.*;
import me.jojiapp.blogserverjh.global.security.filter.*;
import me.jojiapp.blogserverjh.global.security.provider.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfigurationSource corsConfigurationSource;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final ObjectMapper objectMapper;
	private final LoginAuthenticationProvider loginAuthenticationProvider;
	private final JWTAuthorizationProvider jwtAuthorizationProvider;

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		authenticationManagerBuilder.authenticationProvider(loginAuthenticationProvider);
		authenticationManagerBuilder.authenticationProvider(jwtAuthorizationProvider);
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		return new JWTAuthorizationFilter(authenticationManager(), objectMapper);
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
				.configurationSource(corsConfigurationSource)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.headers()
				.frameOptions()
				.disable()
				.and()
				.authorizeRequests(authz -> {
					authz.antMatchers("/docs/index.html")
							.permitAll()
							.antMatchers("/api/auth/**")
							.permitAll()
							.anyRequest().authenticated();
				})
				.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
}
