package me.jojiapp.blogserverjh.global.security.exception;

import me.jojiapp.blogserverjh.global.jwt.error.*;
import org.springframework.security.core.*;

public class JWTAuthenticationException extends AuthenticationException {
	public JWTAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public JWTAuthenticationException(JWTError jwtError) {
		super(jwtError.getMessage());
	}
}
