package me.jojiapp.blogserverjh.global.jwt;

import io.jsonwebtoken.*;
import lombok.*;
import me.jojiapp.blogserverjh.global.jwt.dto.*;
import me.jojiapp.blogserverjh.global.jwt.error.*;

import java.util.*;

@RequiredArgsConstructor
public class JWTProvider {

	private static final String AUTHORITIES_KEY = "roles";
	private final JWTProperties jwtProperties;

	public JWTDTO generate(Long id, String roles) {
		val now = new Date();
		val accessToken = Jwts.builder()
				.setIssuer(id.toString())
				.claim(AUTHORITIES_KEY, roles)
				.setExpiration(jwtProperties.getAccessTokenExpiredDate(now))
				.signWith(jwtProperties.getKey())
				.compact();

		val refreshToken = Jwts.builder()
				.setIssuer(id.toString())
				.setExpiration(jwtProperties.getRefreshTokenExpiredDate(now))
				.signWith(jwtProperties.getKey())
				.compact();

		return new JWTDTO(accessToken, refreshToken);
	}

	public Long getIssuer(String token) {
		return Long.valueOf(getClaims(token).getIssuer());
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(jwtProperties.getKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			throw new JwtException(JWTError.EXPIRED.getMessage(), e);
		} catch (JwtException e) {
			throw new JwtException(JWTError.BAD_TOKEN.getMessage(), e);
		}
	}

	public String getRoles(String token) {
		return (String) getClaims(token).get(AUTHORITIES_KEY);
	}

	public boolean match(String accessToken, String refreshToken) {
		return getIssuer(accessToken).equals(getIssuer(refreshToken));
	}
}
