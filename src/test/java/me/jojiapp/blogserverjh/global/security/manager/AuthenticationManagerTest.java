package me.jojiapp.blogserverjh.global.security.manager;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.repo.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.security.authentication.*;
import me.jojiapp.blogserverjh.support.test.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.crypto.password.*;

import java.util.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;
import static me.jojiapp.blogserverjh.global.security.context.MemberContextGiven.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@TestEnv
public class AuthenticationManagerTest {

	@Autowired
	private JWTProvider jwtProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MemberRepo memberRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("JWTAccessTokenAuthentication을 지원한다")
	void test1() throws Exception {
		// Given
		val jwtResponse = jwtProvider.generate(EMAIL, List.of(RoleType.USER.name()));
		val authenticationToken = JWTAccessTokenAuthenticationToken.from(jwtResponse.accessToken());

		// When
		val authentication = authenticationManager.authenticate(authenticationToken);

		// Then
		assertThat(authentication.getPrincipal()).isEqualTo(EMAIL);
		assertThat(authentication.getCredentials()).isNull();
		assertThat(authentication.getAuthorities().contains(new SimpleGrantedAuthority(ROLE_USER))).isTrue();
	}

	@Test
	@DisplayName("UsernamePasswordAuthenticaionToken을 지원한다")
	void test2() throws Exception {
		// Given

		memberRepo.save(givenMember());
		val authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD);

		// When
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		// Then
		assertThat(authentication.getPrincipal()).isEqualTo(EMAIL);
		assertThat(authentication.getCredentials()).isNull();
		assertThat(authentication.getAuthorities().contains(new SimpleGrantedAuthority(ROLE_USER))).isTrue();
	}

}
