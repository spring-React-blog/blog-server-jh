package me.jojiapp.blogserverjh.global.jwt;

import lombok.*;
import org.springframework.boot.context.properties.*;

import java.time.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.*;

@ConfigurationProperties(prefix = "jwt")
@ConstructorBinding
@RequiredArgsConstructor
@ToString
public class JWTProperties {

	private final String secretKey;
	private final Long accessTokenExpiredMinutes;
	private final Long refreshTokenExpiredMinutes;

	public byte[] toBytesSecretKey() {
		return secretKey.getBytes(UTF_8);
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
