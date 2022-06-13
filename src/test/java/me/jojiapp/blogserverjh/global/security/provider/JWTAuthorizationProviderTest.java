package me.jojiapp.blogserverjh.global.security.provider;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.jwt.error.*;
import me.jojiapp.blogserverjh.global.security.authentication.*;
import me.jojiapp.blogserverjh.global.security.exception.*;
import org.junit.jupiter.api.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;

import java.util.*;

import static me.jojiapp.blogserverjh.global.jwt.JWTPropertiesTest.*;
import static org.assertj.core.api.Assertions.*;

public class JWTAuthorizationProviderTest {

	private static final List<GrantedAuthority> authorities =
			List.of(new SimpleGrantedAuthority("ROLE_%s".formatted(RoleType.USER)));

	private static final List<String> authoritiesString = authorities.stream()
			.map(grantedAuthority ->
					grantedAuthority.getAuthority()
							.substring("ROLE_".length())
			)
			.toList();
	public static final long ID = 1L;

	private JWTProvider jwtProvider;
	private JWTAuthorizationProvider jwtAuthorizationProvider;

	@BeforeEach
	private void setUp() {
		setJwtProvider(1L, 2L);
		setJwtAuthorizationProvider();
	}

	private void setJwtAuthorizationProvider() {
		jwtAuthorizationProvider = new JWTAuthorizationProvider(jwtProvider);
	}

	private void setJwtProvider(Long accessTokenExpiredMinutes, Long refreshTokenExpiredMinutes) {
		val jwtProperties = new JWTProperties(
				SECRET_KEY,
				accessTokenExpiredMinutes,
				refreshTokenExpiredMinutes
		);

		this.jwtProvider = new JWTProvider(jwtProperties);
	}


	@Test
	@DisplayName("JWTAccessTokenAuthentication 클래스를 지원한다")
	void support() throws Exception {
		val actual = jwtAuthorizationProvider.supports(JWTAccessTokenAuthentication.class);
		assertThat(actual).isTrue();
	}

	@Test
	@DisplayName("Access Token을 기반으로 Authentication 객체를 생성하여 반환한다")
	void authenticate() throws Exception {
		// Given
		val jwtDto = jwtProvider.generate(1L, authoritiesString);
		val authentication = JWTAccessTokenAuthentication.of(jwtDto.accessToken());

		// When
		Authentication actual = jwtAuthorizationProvider.authenticate(authentication);

		// Then
		assertThat(actual.getPrincipal()).isEqualTo(ID);
		assertThat(actual.getCredentials()).isNull();
		assertThat(actual.getAuthorities()).isEqualTo(authorities);
	}

	@Test
	@DisplayName("Access Token이 아닐 경우 JWTAuthenticationException이 발생한다")
	void isAccessToken() throws Exception {
		// Given
		val jwtDto = jwtProvider.generate(1L, authoritiesString);
		val authentication = JWTAccessTokenAuthentication.of(jwtDto.refreshToken());

		// When & Then
		assertThatThrownBy(() -> jwtAuthorizationProvider.authenticate(authentication))
				.isInstanceOf(JWTAuthenticationException.class)
				.hasMessage(JWTError.NOT_ACCESS_TOKEN.getMessage());
	}

	@Test
	@DisplayName("Access Token이 만료된 경우 JWTAuthenticationException이 발생한다")
	void expiredAccessToken() throws Exception {
		// Given
		setJwtProvider(-1L, 2L);
		val jwtDto = jwtProvider.generate(1L, authoritiesString);
		val authentication = JWTAccessTokenAuthentication.of(jwtDto.accessToken());

		// When & Then
		assertThatThrownBy(() -> jwtAuthorizationProvider.authenticate(authentication))
				.isInstanceOf(JWTAuthenticationException.class)
				.hasMessage(JWTError.EXPIRED.getMessage());
	}

}
