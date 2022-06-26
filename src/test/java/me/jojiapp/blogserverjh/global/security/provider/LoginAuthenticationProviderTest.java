package me.jojiapp.blogserverjh.global.security.provider;

import lombok.*;
import me.jojiapp.blogserverjh.global.security.exception.*;
import me.jojiapp.blogserverjh.support.test.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;
import static me.jojiapp.blogserverjh.global.security.context.MemberContextGiven.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@UnitTest
class LoginAuthenticationProviderTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserDetailsService userDetailsService;

	@InjectMocks
	private LoginAuthenticationProvider loginAuthenticationProvider;

	@Test
	@DisplayName("UsernamePasswordAuthenticationToken을 지원한다")
	void supports() throws Exception {
		// When
		boolean actual = loginAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class);

		// Then
		assertThat(actual).isTrue();
	}

	@Test
	@DisplayName("모든 인증이 되었을 경우 UsernamePasswordAuthenticationToken을 반환한다")
	void authenticate() throws Exception {
		// Given
		val authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD);
		given(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).willReturn(true);
		given(userDetailsService.loadUserByUsername(EMAIL))
			.willReturn(givenMemberContext());

		// When
		Authentication authenticate = loginAuthenticationProvider.authenticate(authenticationToken);

		// Then
		assertThat(authenticate.getPrincipal()).isEqualTo(EMAIL);
		assertThat(authenticate.getCredentials()).isNull();
		assertThat(authenticate.getAuthorities().contains(new SimpleGrantedAuthority(ROLE_USER))).isTrue();
	}

	@Test
	@DisplayName("비밀번호가 일차하지 않으면 PasswordNotMatchException이 발생한다")
	void passwordNotMatch() throws Exception {
		// Given
		val authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, "rawPassword");
		given(passwordEncoder.matches("rawPassword", ENCODED_PASSWORD)).willReturn(false);
		given(userDetailsService.loadUserByUsername(EMAIL))
			.willReturn(givenMemberContext());

		// When & Then
		assertThatThrownBy(() -> loginAuthenticationProvider.authenticate(authenticationToken))
			.isInstanceOf(PasswordNotMatchException.class);
	}
}
