package me.jojiapp.blogserverjh.global.jwt;


import io.jsonwebtoken.security.*;
import lombok.*;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;

public class JWTPropertiesTest {

	public static final String SECRET_KEY = "ASDAOSDMASMDOASOMDOOMQDWOMQWDMOODQWMOQWD";
	public static final long REFRESH_TOKEN_EXPIRED_MINUTES = 2L;
	private final JWTProperties jwtProperties = new JWTProperties(SECRET_KEY, 1L, REFRESH_TOKEN_EXPIRED_MINUTES);

	@Test
	@DisplayName("서명에 사용될 Key를 조회한다")
	void getKey() throws Exception {
		// Given
		val secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(UTF_8));

		// When
		val actual = jwtProperties.getKey();
		// Then

		assertThat(actual).isEqualTo(secretKey);
	}

	@Test
	@DisplayName("Access Token 만료 시간 조회")
	void getAccessTokenExpiredDate() throws Exception {
		// Given
		val now = new Date();

		// When
		val actual = jwtProperties.getAccessTokenExpiredDate(now);

		// Then
		assertThat(actual).isEqualTo(getActualExpiredDate(now, 1L));
	}

	@Test
	@DisplayName("Refresh Token 만료 시간 조회")
	void getRefreshTokenExpiredDate() throws Exception {
		// Given
		val now = new Date();

		// When
		val actual = jwtProperties.getRefreshTokenExpiredDate(now);

		// Then
		assertThat(actual).isEqualTo(getActualExpiredDate(now, 2L));
	}

	private Date getActualExpiredDate(Date now, Long expiredMinutes) {
		return new Date(now.getTime() + Duration.ofMinutes(expiredMinutes).toMillis());
	}

	@Test
	@DisplayName("Refresh Token 만료 시간 초 던위 조회")
	void getRefreshTokenExpiredSeconds() throws Exception {
		// Given
		long refreshTokenExpiredSeconds = Duration.ofMinutes(REFRESH_TOKEN_EXPIRED_MINUTES).toSeconds();

		// When
		int actual = jwtProperties.getRefreshTokenExpiredSeconds();

		// Then
		assertThat(actual).isEqualTo(refreshTokenExpiredSeconds);
	}
}
