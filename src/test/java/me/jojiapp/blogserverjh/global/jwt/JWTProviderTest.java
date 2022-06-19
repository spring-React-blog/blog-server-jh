package me.jojiapp.blogserverjh.global.jwt;

import io.jsonwebtoken.*;
import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.jwt.dto.response.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static me.jojiapp.blogserverjh.global.jwt.JWTPropertiesTest.*;
import static me.jojiapp.blogserverjh.global.jwt.error.JWTError.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

public class JWTProviderTest {

	private static final String EMAIL = "test@gmail.com";
	private JWTProvider jwtProvider;

	@BeforeEach
	private void setUp() {
		setJwtProvider(1L, 2L);
	}

	private void setJwtProvider(Long accessTokenExpiredMinutes, Long refreshTokenExpiredMinutes) {
		val jwtProperties = new JWTProperties(
				SECRET_KEY,
				accessTokenExpiredMinutes,
				refreshTokenExpiredMinutes
		);

		this.jwtProvider = new JWTProvider(jwtProperties);
	}

	private JWTResponse jwtGenerate() {
		return jwtGenerate(EMAIL);
	}

	private JWTResponse jwtGenerate(String email) {
		return jwtProvider.generate(email, List.of(RoleType.USER.name()));
	}

	@Test
	@DisplayName("회원의 아이디 및 권한을 받아 토큰을 발급한다")
	void generate() throws Exception {
		// When
		val actual = jwtGenerate();

		// Then
		assertThat(actual.accessToken()).isNotBlank();
		assertThat(actual.refreshToken()).isNotBlank();
	}

	@Test
	@DisplayName("토큰 발행자 조회")
	void getIssuer() throws Exception {
		// Given
		val jwtResponse = jwtGenerate();

		// When
		val actual = jwtProvider.getIssuer(jwtResponse.accessToken());

		// Then
		assertThat(actual).isEqualTo(EMAIL);
	}

	@Test
	@DisplayName("토큰 권한 클레임 조회")
	void getRole() throws Exception {
		// Given
		val jwtResponse = jwtGenerate();

		// When
		val actual = jwtProvider.getRoles(jwtResponse.accessToken());

		// Then
		assertThat(actual).isEqualTo(List.of(RoleType.USER.name()));
	}

	@Test
	@DisplayName("토큰이 만료된 경우 ExpiredJwtException이 발생한다")
	void expiredJwtException() throws Exception {
		// Given
		setJwtProvider(-1L, -2L);
		val jwtResponse = jwtGenerate();

		// When & Then
		assertThatThrownBy(() -> jwtProvider.getIssuer(jwtResponse.accessToken()))
				.isInstanceOf(ExpiredJwtException.class)
				.hasMessage(EXPIRED.getMessage());
	}

	@Test
	@DisplayName("클레임이 존재하지 않는 경우 null을 반환한다")
	void claimIsNull() throws Exception {
		// Given
		val jwtResponse = jwtGenerate();

		// When
		val actual = jwtProvider.getRoles(jwtResponse.refreshToken());

		// Then
		assertThat(actual).isEqualTo(List.of());
	}

	@Test
	@DisplayName("잘못된 토큰의 경우 JwtException이 발생한다")
	void badTokenException() throws Exception {
		// Given
		val token = "bad token";

		// When & Then
		assertThatThrownBy(() -> jwtProvider.getIssuer(token))
				.isInstanceOf(JwtException.class)
				.hasMessage(BAD_TOKEN.getMessage());
	}

	@Test
	@DisplayName("Access Token 인지 판단 여부")
	void isAccessToken() throws Exception {
		// Given
		val jwtResponse = jwtGenerate();

		// When & then
		assertThat(jwtProvider.isAccessToken(jwtResponse.accessToken())).isTrue();
		assertThat(jwtProvider.isAccessToken(jwtResponse.refreshToken())).isFalse();
	}

	@Test
	@DisplayName("Refresh Token이 아닐경우 JwtException이 발생한다")
	void validationRefreshToken() throws Exception {
		// Given
		val jwtResponse = jwtGenerate();

		// When & Then
		assertThatThrownBy(() -> jwtProvider.validationRefreshToken(jwtResponse.accessToken()))
				.isInstanceOf(JwtException.class)
				.hasMessage(NOT_REFRESH_TOKEN.getMessage());
	}

	@Nested
	class Match {
		@Test
		@DisplayName("Access Token과 Refresh Token의 Issuer 동일 여부")
		void match() throws Exception {
			// Given
			setJwtProvider(-1L, 2L);
			val jwtResponse1 = jwtGenerate();
			val jwtResponse2 = jwtGenerate("not@gmail.com");

			// When
			val actualMatch = jwtProvider.match(jwtResponse1.accessToken(), jwtResponse1.refreshToken());
			val actualNotMatch = jwtProvider.match(jwtResponse1.accessToken(), jwtResponse2.refreshToken());

			// Then
			assertThat(actualMatch).isTrue();
			assertThat(actualNotMatch).isFalse();
		}

		@Test
		@DisplayName("Access Token이 만료되지 않았으면 JwtException이 발생한다")
		void matchNotExpiredAccessToken() throws Exception {
			// Given
			val jwtResponse = jwtGenerate();

			// When & Then
			assertThatThrownBy(() -> jwtProvider.match(jwtResponse.accessToken(), jwtResponse.refreshToken()))
					.isInstanceOf(JwtException.class)
					.hasMessage(ACCESS_TOKEN_NOT_EXPIRED.getMessage());
		}

		@Test
		@DisplayName("Access Token에 Access Token이 아니면 JwtException이 발생한다")
		void matchValidationAccessToken() throws Exception {
			// Given
			setJwtProvider(-1L, 2L);
			val jwtResponse = jwtGenerate();

			// When & Then
			assertThatThrownBy(() ->
					jwtProvider.match(jwtResponse.refreshToken(), jwtResponse.refreshToken())
			)
					.isInstanceOf(JwtException.class)
					.hasMessage(NOT_ACCESS_TOKEN.getMessage());
		}

		@Test
		@DisplayName("Refresh Token에 Refresh Token이 아니면 JwtException이 발생한다")
		void matchValidationRefreshToken() throws Exception {
			// Given
			setJwtProvider(-1L, 2L);
			val jwtResponse = jwtGenerate();

			// When & Then
			assertThatThrownBy(() ->
					jwtProvider.match(jwtResponse.accessToken(), jwtResponse.accessToken())
			)
					.isInstanceOf(JwtException.class)
					.hasMessage(NOT_REFRESH_TOKEN.getMessage());
		}
	}
}
