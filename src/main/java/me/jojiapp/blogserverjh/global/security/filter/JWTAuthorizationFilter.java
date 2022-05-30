package me.jojiapp.blogserverjh.global.security.filter;

import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.web.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.springframework.http.HttpHeaders.*;

/**
 * JWT 인가 처리 필터
 */
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	/**
	 * 보안 토큰
	 * <p>
	 * Authorization Header의 첫 시작은 Bearer 이여야 합니다.
	 */
	public static final String BEARER = "Bearer ";
	/**
	 * 인증 처리 위임 매니저
	 */
	private final AuthenticationManager authenticationManager;

	/**
	 * 인가 처리 필터
	 *
	 * @param request     HTTP 요청 객체
	 * @param response    HTTP 응답 객체
	 * @param filterChain 시큐리티 필터 체인
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
		val authorization = request.getHeader(AUTHORIZATION);
		if (isNotAuthorizationStartWithBearer(authorization)) {
			filterChain.doFilter(request, response);
			return;
		}

		val accessToken = authorization.substring(BEARER.length());

//		authenticationManager.authenticate()

	}

	/**
	 * @param authorization Authorization Header Value
	 *
	 * @return Authorization Header의 시작이 Bearer 인지 여부 판단
	 */
	private boolean isNotAuthorizationStartWithBearer(final String authorization) {
		return Optional.ofNullable(authorization)
				.map(a -> !a.startsWith(BEARER))
				.orElse(true);

	}
}
