package me.jojiapp.blogserverjh.domain.auth.api;

import lombok.*;
import me.jojiapp.blogserverjh.domain.auth.dto.request.*;
import me.jojiapp.blogserverjh.domain.auth.dto.response.*;
import me.jojiapp.blogserverjh.domain.auth.service.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.response.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;

@RestController
@RequestMapping("/api/public/auth")
@RequiredArgsConstructor
public class AuthAPI {

	private final AuthService authService;
	private final JWTProperties jwtProperties;

	@PostMapping("/login")
	private APIResponse<AccessTokenResponse> login(
			@RequestBody @Valid final MemberLogin memberLogin,
			final HttpServletResponse response
	) {
		val jwtResponse = authService.login(memberLogin);
		response.addCookie(createRefreshTokenCookie(jwtResponse.refreshToken()));
		return APIResponse.success(new AccessTokenResponse(jwtResponse.accessToken()));
	}

	private Cookie createRefreshTokenCookie(final String refreshToken) {
		val REFRESH_TOKEN = "refreshToken";
		val cookie = new Cookie(REFRESH_TOKEN, refreshToken);
		cookie.setMaxAge(jwtProperties.getRefreshTokenExpiredSeconds());
		cookie.setDomain("localhost:8080");
		return cookie;
	}
}
