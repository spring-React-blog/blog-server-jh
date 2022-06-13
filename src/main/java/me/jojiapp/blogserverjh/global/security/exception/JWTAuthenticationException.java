package me.jojiapp.blogserverjh.global.security.exception;

import me.jojiapp.blogserverjh.global.jwt.error.*;
import org.springframework.security.core.*;

public class JWTAuthenticationException extends AuthenticationException {
	public JWTAuthenticationException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public JWTAuthenticationException(final JWTError jwtError) {
		super(jwtError.getMessage());
	}
}
