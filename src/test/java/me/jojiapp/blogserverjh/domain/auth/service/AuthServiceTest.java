package me.jojiapp.blogserverjh.domain.auth.service;

import lombok.*;
import me.jojiapp.blogserverjh.domain.auth.dto.request.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.security.context.*;
import me.jojiapp.blogserverjh.support.test.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.authentication.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;
import static me.jojiapp.blogserverjh.global.jwt.JWTPropertiesTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@UnitTest
class AuthServiceTest {

	@Mock
	private AuthenticationManager authenticationManager;

	private JWTProvider jwtProvider;

	private AuthService authService;

	@BeforeEach
	private void setUp() {
		val jwtProperties = new JWTProperties(
				SECRET_KEY,
				1L,
				2L
		);

		this.jwtProvider = new JWTProvider(jwtProperties);
		this.authService = new AuthService(authenticationManager, jwtProvider);
	}

	@Test
	@DisplayName("이메일과 비밀번호를 통해 로그인에 성공하면 AccessToken과 RefreshToken을 반환한다")
	void login() throws Exception {
		// Given
		val memberLogin = new MemberLogin(
				EMAIL,
				PASSWORD
		);
		val authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD);
		val authenticate = new UsernamePasswordAuthenticationToken(EMAIL, null, MemberContext.parseAuthorities(RoleType.USER));
		given(authenticationManager.authenticate(authenticationToken)).willReturn(authenticate);

		// When
		val jwtResponse = authService.login(memberLogin);

		// Then
		assertThat(jwtProvider.getIssuer(jwtResponse.accessToken())).isEqualTo(EMAIL);
		assertThat(jwtProvider.getRoles(jwtResponse.accessToken())).contains("ROLE_" + RoleType.USER.name());
		assertThat(jwtProvider.isAccessToken(jwtResponse.refreshToken())).isFalse();
	}

}
