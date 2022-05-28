package me.jojiapp.blogserverjh.global.jwt;

import io.jsonwebtoken.*;
import lombok.*;
import me.jojiapp.blogserverjh.global.jwt.dto.*;

import java.util.*;

import static me.jojiapp.blogserverjh.global.jwt.error.JWTError.*;

/**
 * JWT
 */
@RequiredArgsConstructor
public class JWTProvider {

	/**
	 * 권한 클레임 Key
	 */
	private static final String AUTHORITIES_KEY = "roles";

	/**
	 * JWT 설정
	 *
	 * @see JWTProperties JWT 설정 클래스
	 */
	private final JWTProperties jwtProperties;

	/**
	 * 토큰 생성
	 *
	 * @param issuerId 발급자 고유 아이디
	 * @param roles    권한
	 *
	 * @return Access Token, Refresh Token
	 *
	 * @see JWTDTO
	 */
	public JWTDTO generate(Long issuerId, String roles) {
		val now = new Date();
		val accessToken = Jwts.builder()
				.setIssuer(issuerId.toString())
				.claim(AUTHORITIES_KEY, roles)
				.setExpiration(jwtProperties.getAccessTokenExpiredDate(now))
				.signWith(jwtProperties.getKey())
				.compact();

		val refreshToken = Jwts.builder()
				.setIssuer(issuerId.toString())
				.setExpiration(jwtProperties.getRefreshTokenExpiredDate(now))
				.signWith(jwtProperties.getKey())
				.compact();

		return new JWTDTO(accessToken, refreshToken);
	}

	/**
	 * 발급자 조회
	 *
	 * @param token JWT로 인코딩 된 토큰
	 *
	 * @return 발급자 고유 아이디
	 */
	public Long getIssuer(String token) {
		return Long.valueOf(getClaims(token).getIssuer());
	}

	/**
	 * 클레임 조회
	 *
	 * @param token JWT로 인코딩 된 토큰
	 *
	 * @return 전체 클레임
	 *
	 * @throws JwtException 토큰을 사용하기에 문제가 있을 경우 발생하는 예외
	 */
	private Claims getClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(jwtProperties.getKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			throw new ExpiredJwtException(e.getHeader(), e.getClaims(), EXPIRED.getMessage(), e);
		} catch (JwtException e) {
			throw new JwtException(BAD_TOKEN.getMessage(), e);
		}
	}

	/**
	 * 권한 조회
	 *
	 * @param token JWT로 인코딩 된 토큰
	 *
	 * @return 권한
	 */
	public String getRoles(String token) {
		return (String) getClaims(token).get(AUTHORITIES_KEY);
	}

	/**
	 * 토큰 발급자 일치 여부
	 *
	 * @param accessToken  만료된 Access Token
	 * @param refreshToken 만료되지 않은 Refresh Token
	 *
	 * @return Access Token과 Refresh Token의 Issuer 동일 여부
	 */
	public boolean match(String accessToken, String refreshToken) {
		validationAccessToken(accessToken);
		validationRefreshToken(refreshToken);
		try {
			getIssuer(accessToken);
			throw new JwtException(ACCESS_TOKEN_NOT_EXPIRED.getMessage());
		} catch (ExpiredJwtException e) {
			return Long.valueOf(e.getClaims().getIssuer()).equals(getIssuer(refreshToken));
		}
	}

	/**
	 * 해당 토큰이 Access Token 인지 여부
	 *
	 * @param token JWT로 인코딩 된 토큰
	 *
	 * @return Access Token 여부
	 */
	private boolean isAccessToken(String token) {
		try {
			return getRoles(token) != null;
		} catch (ExpiredJwtException e) {
			return e.getClaims().get(AUTHORITIES_KEY) != null;
		}
	}

	/**
	 * Access Token인지 유효성 검사
	 *
	 * @param accessToken JWT로 인코딩 된 Access Token
	 *
	 * @throws JwtException
	 */
	private void validationAccessToken(String accessToken) {
		if (!isAccessToken(accessToken)) throw new JwtException(NOT_ACCESS_TOKEN.getMessage());
	}

	/**
	 * Refresh Token인지 유효성 검사
	 *
	 * @param refreshToken JWT로 인코딩 된 Refresh Token
	 *
	 * @throws JwtException
	 */
	private void validationRefreshToken(String refreshToken) {
		if (isAccessToken(refreshToken)) throw new JwtException(NOT_REFRESH_TOKEN.getMessage());
	}
}
