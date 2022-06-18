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
import org.springframework.stereotype.*;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationProvider implements AuthenticationProvider {

	private final JWTProvider jwtProvider;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		val accessToken = (String) authentication.getPrincipal();
		if (!jwtProvider.isAccessToken(accessToken))
			throw new JWTAuthenticationException(JWTError.NOT_ACCESS_TOKEN);
		try {
			return createAuthenticationByAccessToken(accessToken);
		} catch (JwtException e) {
			throw new JWTAuthenticationException(e.getMessage(), e);
		}

	}

	private Authentication createAuthenticationByAccessToken(final String accessToken) {
		return new UsernamePasswordAuthenticationToken(
				jwtProvider.getIssuer(accessToken),
				null,
				MemberContext.parseAuthorities(jwtProvider.getRoles(accessToken))
		);
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.isAssignableFrom(JWTAccessTokenAuthenticationToken.class);
	}
}
