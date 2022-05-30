package me.jojiapp.blogserverjh.global.security.provider;

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

	private final JWTProvider jwtProvider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		val accessToken = (String) authentication.getPrincipal();
		if (!jwtProvider.isAccessToken(accessToken))
			throw new JWTAuthenticationException(JWTError.NOT_ACCESS_TOKEN);

		return new UsernamePasswordAuthenticationToken(
				jwtProvider.getIssuer(accessToken),
				null,
				MemberContext.parseAuthorities(jwtProvider.getRoles(accessToken))
		);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JWTAccessTokenAuthentication.class);
	}
}
