package me.jojiapp.blogserverjh.global.security.authentication;

import org.springframework.security.authentication.*;

/**
 * Access Token Authentication 클래스
 * <p>
 * Access Token을 전달받아 인증 객체를 생성하여 인증 처리 진행
 */
public final class JWTAccessTokenAuthentication extends AbstractAuthenticationToken {
	private final String accessToken;


	/**
	 * @param accessToken 인증을 처리할 Access Token
	 */
	private JWTAccessTokenAuthentication(final String accessToken) {
		super(null);
		this.accessToken = accessToken;
	}

	/**
	 * @param accessToken 인증을 처리할 Access Token
	 *
	 * @return 인증 객체
	 *
	 * @see JWTAccessTokenAuthentication
	 */
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
