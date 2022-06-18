package me.jojiapp.blogserverjh.global.jwt;

import io.jsonwebtoken.*;
import lombok.*;
import me.jojiapp.blogserverjh.global.jwt.dto.response.*;
import org.springframework.stereotype.*;

import java.util.*;

import static me.jojiapp.blogserverjh.global.jwt.error.JWTError.*;

@RequiredArgsConstructor
@Component
public class JWTProvider {

	private static final String AUTHORITIES_KEY = "roles";

	private static final String delimiter = ", ";

	private final JWTProperties jwtProperties;

	public JWTResponse generate(final String issuer, final List<String> roles) {
		val now = new Date();
		val accessToken = Jwts.builder()
				.setIssuer(issuer)
				.claim(AUTHORITIES_KEY, String.join(delimiter, roles))
				.setExpiration(jwtProperties.getAccessTokenExpiredDate(now))
				.signWith(jwtProperties.getKey())
				.compact();

		val refreshToken = Jwts.builder()
				.setIssuer(issuer)
				.setExpiration(jwtProperties.getRefreshTokenExpiredDate(now))
				.signWith(jwtProperties.getKey())
				.compact();

		return new JWTResponse(accessToken, refreshToken);
	}

	public String getIssuer(final String token) {
		return getClaims(token).getIssuer();
	}

	private Claims getClaims(final String token) {
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

	public List<String> getRoles(final String token) {
		val rolesJoin = (String) getClaims(token).get(AUTHORITIES_KEY);
		if (rolesJoin == null) return List.of();
		return Arrays.stream(rolesJoin.split(delimiter)).toList();
	}

	public boolean match(final String accessToken, final String refreshToken) {
		validationAccessToken(accessToken);
		validationRefreshToken(refreshToken);
		try {
			getIssuer(accessToken);
			throw new JwtException(ACCESS_TOKEN_NOT_EXPIRED.getMessage());
		} catch (ExpiredJwtException e) {
			return e.getClaims().getIssuer().equals(getIssuer(refreshToken));
		}
	}

	public boolean isAccessToken(final String token) {
		try {
			return !getRoles(token).equals(List.of());
		} catch (ExpiredJwtException e) {
			return e.getClaims().get(AUTHORITIES_KEY) != null;
		}
	}

	private void validationAccessToken(final String accessToken) {
		if (!isAccessToken(accessToken)) throw new JwtException(NOT_ACCESS_TOKEN.getMessage());
	}

	private void validationRefreshToken(final String refreshToken) {
		if (isAccessToken(refreshToken)) throw new JwtException(NOT_REFRESH_TOKEN.getMessage());
	}
}
