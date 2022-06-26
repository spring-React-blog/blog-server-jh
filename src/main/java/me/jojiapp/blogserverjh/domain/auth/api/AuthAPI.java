package me.jojiapp.blogserverjh.domain.auth.api;

import lombok.*;
import me.jojiapp.blogserverjh.domain.auth.dto.request.*;
import me.jojiapp.blogserverjh.domain.auth.dto.response.*;
import me.jojiapp.blogserverjh.domain.auth.service.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.cookie.*;
import me.jojiapp.blogserverjh.global.response.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;

@RestController
@RequestMapping("/api/public/auth")
@RequiredArgsConstructor
public class AuthAPI {

	private final AuthService authService;
	private final CookieProvider cookieProvider;

	@PostMapping("/login")
	private APIResponse<AccessTokenResponse> login(
			@RequestBody @Valid final MemberLogin memberLogin,
			final HttpServletResponse response
	) {
		val jwtResponse = authService.login(Email.from(memberLogin.email()), Password.from(memberLogin.password()));
		response.addCookie(cookieProvider.createRefreshToken(jwtResponse.refreshToken()));
		return APIResponse.success(new AccessTokenResponse(jwtResponse.accessToken()));
	}

	@PostMapping("/refresh")
	private APIResponse<AccessTokenResponse> refresh(
			@CookieValue(name = "refreshToken") final String refreshToken,
			final HttpServletResponse response
	) {
		val jwtResponse = authService.refresh(refreshToken);
		response.addCookie(cookieProvider.createRefreshToken(jwtResponse.refreshToken()));
		return APIResponse.success(new AccessTokenResponse(jwtResponse.accessToken()));
	}
}
