package me.jojiapp.blogserverjh.domain.auth.service;

import io.jsonwebtoken.*;
import lombok.*;
import me.jojiapp.blogserverjh.domain.member.repo.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.security.context.*;
import me.jojiapp.blogserverjh.support.test.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.authentication.*;

import java.util.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;
import static me.jojiapp.blogserverjh.global.jwt.JWTPropertiesTest.*;
import static me.jojiapp.blogserverjh.global.jwt.error.JWTError.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@UnitTest
class AuthServiceTest {

	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private MemberRepo memberRepo;

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
		this.authService = new AuthService(authenticationManager, jwtProvider, memberRepo);
	}

	@Test
	@DisplayName("이메일과 비밀번호를 통해 로그인에 성공하면 AccessToken과 RefreshToken을 반환한다")
	void login() throws Exception {
		// Given
		val authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD);
		val authenticate = new UsernamePasswordAuthenticationToken(EMAIL, null, MemberContext.parseAuthorities(RoleType.USER));
		given(authenticationManager.authenticate(authenticationToken)).willReturn(authenticate);

		// When
		val actual = authService.login(Email.from(EMAIL), Password.from(PASSWORD));

		// Then
		assertThat(jwtProvider.getIssuer(actual.accessToken())).isEqualTo(EMAIL);
		assertThat(jwtProvider.getRoles(actual.accessToken())).contains(RoleType.USER.name());
		assertThat(jwtProvider.getIssuer(actual.refreshToken())).isEqualTo(EMAIL);
		assertThat(jwtProvider.isAccessToken(actual.refreshToken())).isFalse();
	}


	@Nested
	class Refresh {

		@Test
		@DisplayName("Refresh Token을 전달받아 AccessToken과 Refresh Token을 반환한다")
		void refresh() throws Exception {
			// Given
			val jwtResponse = jwtProvider.generate(EMAIL, List.of(RoleType.USER.name()));
			given(memberRepo.findLoginAuthByEmail(Email.from(EMAIL))).willReturn(Optional.of(MemberContextGiven.givenLoginAuth()));

			// When
			val actual = authService.refresh(jwtResponse.refreshToken());

			// Then
			assertThat(jwtProvider.getIssuer(actual.accessToken())).isEqualTo(EMAIL);
			assertThat(jwtProvider.getRoles(actual.accessToken())).contains(RoleType.USER.name());
			assertThat(jwtProvider.getIssuer(actual.refreshToken())).isEqualTo(EMAIL);
			assertThat(jwtProvider.isAccessToken(actual.refreshToken())).isFalse();
		}

		@Test
		@DisplayName("Refresh Token이 아닐경우 JwtException이 발생한다")
		void notRefreshToken() throws Exception {
			// Given
			val jwtResponse = jwtProvider.generate(EMAIL, List.of(RoleType.USER.name()));

			// When & Then
			assertThatThrownBy(() -> authService.refresh(jwtResponse.accessToken()))
					.isInstanceOf(JwtException.class)
					.hasMessage(NOT_REFRESH_TOKEN.getMessage());
		}

	}

}
