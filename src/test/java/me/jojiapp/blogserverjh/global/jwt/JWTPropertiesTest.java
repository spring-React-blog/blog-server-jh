package me.jojiapp.blogserverjh.global.jwt;


import lombok.*;
import me.jojiapp.support.*;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;
import static org.assertj.core.api.Assertions.*;

@TestEnv
public class JWTPropertiesTest {

	private final JWTProperties jwtProperties = new JWTProperties("secret", 1L, 2L);

	@Test
	@DisplayName("Secret Key 값을 Bytes로 변환하여 반환한다")
	void toBytesSecretKey() throws Exception {
		assertThat(jwtProperties.toBytesSecretKey()).isEqualTo("secret".getBytes(UTF_8));
	}

	@Test
	@DisplayName("Access Token 만료 시간 조회")
	void getAccessTokenExpiredDate() throws Exception {
		// Given
		val now = new Date();

		// When
		Date actual = jwtProperties.getAccessTokenExpiredDate(now);

		// Then
		assertThat(actual).isEqualTo(getActualExpiredDate(now, 1L));
	}

	@Test
	@DisplayName("Refresh Token 만료 시간 조회")
	void getRefreshTokenExpiredDate() throws Exception {
		// Given
		val now = new Date();

		// When
		Date actual = jwtProperties.getRefreshTokenExpiredDate(now);

		// Then
		assertThat(actual).isEqualTo(getActualExpiredDate(now, 2L));
	}

	private Date getActualExpiredDate(Date now, Long expiredMinutes) {
		return new Date(now.getTime() + Duration.ofMinutes(expiredMinutes).toMillis());
	}


}
