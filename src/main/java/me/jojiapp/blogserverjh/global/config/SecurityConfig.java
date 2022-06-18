package me.jojiapp.blogserverjh.global.config;

import com.fasterxml.jackson.databind.*;
import lombok.*;
import me.jojiapp.blogserverjh.domain.member.repo.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.security.filter.*;
import me.jojiapp.blogserverjh.global.security.provider.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.factory.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.*;

import static org.springframework.web.cors.CorsConfiguration.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JWTProvider jwtProvider;
	private final AuthenticationConfiguration authenticationConfiguration;
	private final ObjectMapper objectMapper;
	private final MemberRepo memberRepo;

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
	public JWTAuthorizationProvider jwtAuthorizationProvider() {
		return new JWTAuthorizationProvider(jwtProvider);
	}

	@Bean
	public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		return new JWTAuthorizationFilter(authenticationManager(authenticationConfiguration), objectMapper);
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
				.authorizeRequests(authz -> {
					authz.antMatchers("/docs/index.html")
							.permitAll()
							.anyRequest().authenticated();
				})
				.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authenticationProvider(jwtAuthorizationProvider())
				.build();
	}
}
