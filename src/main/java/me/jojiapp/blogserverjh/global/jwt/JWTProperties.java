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

	public JWTProperties(String secretKey, Long accessTokenExpiredMinutes, Long refreshTokenExpiredMinutes) {
		this.accessTokenExpiredMinutes = accessTokenExpiredMinutes;
		this.refreshTokenExpiredMinutes = refreshTokenExpiredMinutes;
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
	}

	public Key getKey() {
		return key;
	}

	public Date getAccessTokenExpiredDate(Date now) {
		return getExpiredDate(now, accessTokenExpiredMinutes);
	}

	public Date getRefreshTokenExpiredDate(Date now) {
		return getExpiredDate(now, refreshTokenExpiredMinutes);
	}

	private Date getExpiredDate(Date now, Long expiredMinutes) {
		return new Date(now.getTime() + Duration.ofMinutes(expiredMinutes).toMillis());
	}

}
