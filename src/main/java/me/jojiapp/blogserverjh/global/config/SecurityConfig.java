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

/**
 * 인증/인가 설정 클래스
 */
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Password Encoder 빈
	 *
	 * @return bcrypt Password Encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * CORS 정책 설정 빈
	 *
	 * @return CORS 설정 소스
	 */
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

	/**
	 * WEB 설정 빈
	 *
	 * @return WEB 설정 커스텀 클래스
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
				.antMatchers("/favicon.ico", "/docs/**");
	}

	/**
	 * 인증/인가 필터 체인 빈
	 *
	 * @param http http 인증/인가 설정
	 *
	 * @return http 인증/인가 설정 빈
	 *
	 * @throws Exception
	 */
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
