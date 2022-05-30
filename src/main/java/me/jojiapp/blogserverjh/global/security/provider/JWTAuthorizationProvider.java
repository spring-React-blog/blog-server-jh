package me.jojiapp.blogserverjh.global.security.provider;

import io.jsonwebtoken.*;
import lombok.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.jwt.error.*;
import me.jojiapp.blogserverjh.global.security.authentication.*;
import me.jojiapp.blogserverjh.global.security.context.*;
import me.jojiapp.blogserverjh.global.security.exception.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;

/**
 * JWT 인가 처리 프로바이더
 * <p>
 * JWT를 이용하여 Authentication 객체를 생성 반환
 */
@RequiredArgsConstructor
public class JWTAuthorizationProvider implements AuthenticationProvider {

	/**
	 * JWT Provider
	 */
	private final JWTProvider jwtProvider;

	/**
	 * JWTAccessTokenAuthentication 객체를 전달받아
	 * principal에 담겨있는 Access Token 기반 정보로 UsernamePasswordAuthenticationToken 객체를 생성하여 반환
	 *
	 * @param authentication JWTAccessTokenAuthentication
	 *
	 * @return UsernamePasswordAuthenticationToken
	 *
	 * @throws AuthenticationException
	 * @see Authentication
	 * @see JWTAccessTokenAuthentication
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		val accessToken = (String) authentication.getPrincipal();
		if (!jwtProvider.isAccessToken(accessToken))
			throw new JWTAuthenticationException(JWTError.NOT_ACCESS_TOKEN);
		try {
			return createAuthenticationByAccessToken(accessToken);
		} catch (JwtException e) {
			throw new JWTAuthenticationException(e.getMessage(), e);
		}

	}

	/**
	 * Access Token으로 Authentication 객체를 생성
	 *
	 * @param accessToken 인증에 필요한 Access Token
	 *
	 * @return Authentication
	 *
	 * @see Authentication
	 */
	private Authentication createAuthenticationByAccessToken(String accessToken) {
		return new UsernamePasswordAuthenticationToken(
				jwtProvider.getIssuer(accessToken),
				null,
				MemberContext.parseAuthorities(jwtProvider.getRoles(accessToken))
		);
	}

	/**
	 * @param authentication 인증 객체
	 *
	 * @return JWTAccessTokenAuthentication 클래스를 지원하는지 판단 여부
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JWTAccessTokenAuthentication.class);
	}
}
