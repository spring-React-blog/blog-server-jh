package me.jojiapp.blogserverjh.global.cookie;

import lombok.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;

@Component
@RequiredArgsConstructor
public class CookieProvider {

	private static final String REFRESH_TOKEN = "refreshToken";
	private final CookieProperties cookieProperties;
	private final JWTProperties jwtProperties;


	public Cookie createRefreshToken(final String refreshToken) {
		Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
		cookie.setDomain(cookieProperties.getDomain());
		cookie.setHttpOnly(cookieProperties.isHttpOnly());
		cookie.setSecure(cookieProperties.isSecure());
		cookie.setMaxAge(jwtProperties.getRefreshTokenExpiredSeconds());
		return cookie;
	}

}
