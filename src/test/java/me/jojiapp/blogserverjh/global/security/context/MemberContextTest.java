package me.jojiapp.blogserverjh.global.security.context;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import org.junit.jupiter.api.*;
import org.springframework.security.core.authority.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class MemberContextTest {

	public static final String ROLE_USER = "ROLE_%s".formatted(Role.USER);

	@Test
	@DisplayName("MemberContext에 Member 정보가 정상적으로 할당된다")
	void newMemberContext() throws Exception {
		// Given
		val id = 1L;
		val email = "email";
		val password = "encoding password";
		val memberAuth = new MemberAuth(
				id,
				new Email(email),
				new Password(password),
				Role.USER
		);
		// When
		val memberContext = MemberContext.of(memberAuth);

		// Then
		assertThat(memberContext.getId()).isEqualTo(id);
		assertThat(memberContext.getUsername()).isEqualTo(email);
		assertThat(memberContext.getPassword()).isEqualTo(password);
		assertThat(memberContext.getAuthorities())
				.contains(new SimpleGrantedAuthority(ROLE_USER));
	}

	@Test
	@DisplayName("단건의 권한으로 authorities를 생성한다")
	void parseAuthoritiesSimple() throws Exception {
		// When
		val authorities = MemberContext.parseAuthorities(Role.USER);

		// Then
		assertThat(authorities.get(0).getAuthority()).isEqualTo(ROLE_USER);
	}

	@Test
	@DisplayName("String 리스트로 된 권한 목록을 받아 authorities를 생성한다")
	void parseAuthoritiesStringList() throws Exception {
		// Given
		List<String> roles = List.of(Role.USER.name());

		// When
		val authorities = MemberContext.parseAuthorities(roles);

		// Then
		assertThat(authorities.get(0).getAuthority())
				.isEqualTo("ROLE_%s".formatted(roles.get(0)));
	}

}
