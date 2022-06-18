package me.jojiapp.blogserverjh.global.security.filter;

import com.fasterxml.jackson.databind.*;
import lombok.*;
import lombok.extern.slf4j.*;
import me.jojiapp.blogserverjh.global.response.*;
import me.jojiapp.blogserverjh.global.security.authentication.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.springframework.http.HttpHeaders.*;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	public static final String BEARER = "Bearer ";
	public static final String UTF_8 = "UTF-8";
	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
		val authorization = request.getHeader(AUTHORIZATION);
		val accessToken = authorization.substring(BEARER.length());
		if (isNotAuthorizationStartWithBearer(authorization)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			val authenticate = authenticationManager
					.authenticate(JWTAccessTokenAuthenticationToken.from(accessToken));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
		} catch (AuthenticationException e) {
			log.error("message", e);
			response.setStatus(HttpStatus.GONE.value());
			response.setCharacterEncoding(UTF_8);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			objectMapper.writeValue(response.getWriter(), APIResponse.error(e.getMessage()));
		}

	}

	private boolean isNotAuthorizationStartWithBearer(final String authorization) {
		return Optional.ofNullable(authorization)
				.map(a -> !a.startsWith(BEARER))
				.orElse(true);
	}
}
