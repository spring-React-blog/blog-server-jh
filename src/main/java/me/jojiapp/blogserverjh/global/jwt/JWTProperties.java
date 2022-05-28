package me.jojiapp.blogserverjh.global.jwt;

import io.jsonwebtoken.security.*;
import org.springframework.boot.context.properties.*;

import java.security.*;
import java.time.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

/**
 * JWT 설정 클래스
 * <p>
 * application.yml 설정 파일에서 값을 읽어와 주입
 */
@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JWTProperties {

	/**
	 * Access Token 만료 시간 (분)
	 */
	private final Long accessTokenExpiredMinutes;

	/**
	 * Refresh Token 만료 시간 (분)
	 */
	private final Long refreshTokenExpiredMinutes;

	/**
	 * 서명 키
	 */
	private final Key key;

	/**
	 * 생성자
	 * <p>
	 * 암호 키를 이용하여 서명키를 생성함
	 *
	 * @param secretKey                  암호 키
	 * @param accessTokenExpiredMinutes  Access Token 만료 시간 (분)
	 * @param refreshTokenExpiredMinutes Refresh Token 만료 시간 (분)
	 */
	public JWTProperties(String secretKey, Long accessTokenExpiredMinutes, Long refreshTokenExpiredMinutes) {
		this.accessTokenExpiredMinutes = accessTokenExpiredMinutes;
		this.refreshTokenExpiredMinutes = refreshTokenExpiredMinutes;
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
	}

	/**
	 * 서명 키 조회
	 *
	 * @return 서명 키
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * Access Token 만료 시점
	 *
	 * @param now 발급 시점
	 *
	 * @return 발급 시점 + Access Token 만료 시간
	 */
	public Date getAccessTokenExpiredDate(Date now) {
		return getExpiredDate(now, accessTokenExpiredMinutes);
	}

	/**
	 * Refresh Token 만료 시점
	 *
	 * @param now 발급 시점
	 *
	 * @return 발급 시점 + Refresh Token 만료 시간
	 */
	public Date getRefreshTokenExpiredDate(Date now) {
		return getExpiredDate(now, refreshTokenExpiredMinutes);
	}

	/**
	 * Token 만료 시점
	 *
	 * @param now            발급 시점
	 * @param expiredMinutes 토큰 만료 시간 (분)
	 *
	 * @return 발급 시점 + 토큰 만료 시간
	 */
	private Date getExpiredDate(Date now, Long expiredMinutes) {
		return new Date(now.getTime() + Duration.ofMinutes(expiredMinutes).toMillis());
	}

}
