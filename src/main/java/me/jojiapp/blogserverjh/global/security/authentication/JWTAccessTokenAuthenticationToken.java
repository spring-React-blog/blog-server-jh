package me.jojiapp.blogserverjh.global.security.authentication;

import lombok.*;
import org.springframework.security.authentication.*;

@EqualsAndHashCode(callSuper = false)
public final class JWTAccessTokenAuthenticationToken extends AbstractAuthenticationToken {
	private final String accessToken;

	private JWTAccessTokenAuthenticationToken(final String accessToken) {
		super(null);
		this.accessToken = accessToken;
	}

	public static JWTAccessTokenAuthenticationToken from(final String accessToken) {
		return new JWTAccessTokenAuthenticationToken(accessToken);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public String getPrincipal() {
		return this.accessToken;
	}
}
