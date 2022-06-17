package me.jojiapp.blogserverjh.global.security.authentication;

import org.springframework.security.authentication.*;

public final class JWTAccessTokenAuthentication extends AbstractAuthenticationToken {
	private final String accessToken;

	private JWTAccessTokenAuthentication(final String accessToken) {
		super(null);
		this.accessToken = accessToken;
	}

	public static JWTAccessTokenAuthentication of(final String accessToken) {
		return new JWTAccessTokenAuthentication(accessToken);
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
