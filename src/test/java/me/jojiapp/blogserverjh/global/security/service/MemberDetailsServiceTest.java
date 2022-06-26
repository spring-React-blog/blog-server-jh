package me.jojiapp.blogserverjh.global.security.service;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.exception.*;
import me.jojiapp.blogserverjh.domain.member.repo.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.security.context.*;
import me.jojiapp.blogserverjh.support.test.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@UnitTest
class MemberDetailsServiceTest {

	@Mock
	private MemberRepo memberRepo;

	@InjectMocks
	private MemberDetailsService memberDetailsService;

	@Test
	@DisplayName("email에 해당하는 회원이 없으면 UsernameNotFoundException이 발생한다")
	void test1() throws Exception {
		// When & Then
		assertThatThrownBy(() -> memberDetailsService.loadUserByUsername("not@gmail.com"))
			.isInstanceOf(MemberNotFoundException.class);
	}

	@Test
	@DisplayName("email에 해당하는 회원이 존재하면 MemberContext를 반환한다")
	void test2() throws Exception {
		// Given
		val loginAuth = new LoginAuth(
			Email.from(EMAIL),
			Password.from(PASSWORD),
			RoleType.USER
		);
		given(memberRepo.findLoginAuthByEmail(Email.from(EMAIL)))
			.willReturn(Optional.of(loginAuth));

		// When
		val userDetails = memberDetailsService.loadUserByUsername(EMAIL);

		// Then
		assertThat(userDetails.getUsername()).isEqualTo(EMAIL);
		assertThat(userDetails.getPassword()).isEqualTo(PASSWORD);
		assertThat(userDetails.getAuthorities().contains(MemberContext.getAuthority(RoleType.USER.name()))).isTrue();

	}

}
