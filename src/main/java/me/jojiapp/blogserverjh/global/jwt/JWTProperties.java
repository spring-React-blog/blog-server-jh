package me.jojiapp.blogserverjh.global.jwt;

import io.jsonwebtoken.security.*;
import org.springframework.boot.context.properties.*;

import java.security.*;
import java.time.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
public class JWTProperties {
	private final Long accessTokenExpiredMinutes;

	private final Long refreshTokenExpiredMinutes;

	private final Key key;

	public JWTProperties(final String secretKey, final Long accessTokenExpiredMinutes, final Long refreshTokenExpiredMinutes) {
		this.accessTokenExpiredMinutes = accessTokenExpiredMinutes;
		this.refreshTokenExpiredMinutes = refreshTokenExpiredMinutes;
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
	}

	public Key getKey() {
		return key;
	}

	public Date getAccessTokenExpiredDate(final Date now) {
		return getExpiredDate(now, accessTokenExpiredMinutes);
	}

	public Date getRefreshTokenExpiredDate(final Date now) {
		return getExpiredDate(now, refreshTokenExpiredMinutes);
	}

	public int getRefreshTokenExpiredSeconds() {
		return (int) Duration.ofMinutes(refreshTokenExpiredMinutes).toSeconds();
	}

	private Date getExpiredDate(final Date now, final Long expiredMinutes) {
		return new Date(now.getTime() + Duration.ofMinutes(expiredMinutes).toMillis());
	}

}
