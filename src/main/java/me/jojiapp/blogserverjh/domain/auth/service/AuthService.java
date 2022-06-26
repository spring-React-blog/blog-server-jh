package me.jojiapp.blogserverjh.domain.auth.service;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.exception.*;
import me.jojiapp.blogserverjh.domain.member.repo.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.jwt.dto.response.*;
import me.jojiapp.blogserverjh.global.security.context.*;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JWTProvider jwtProvider;
	private final MemberRepo memberRepo;

	public JWTResponse login(final Email email, final Password password) {
		val authenticate = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(email.getEmail(), password.getPassword())
		);
		val roles = authenticate.getAuthorities()
			.stream()
			.map(grantedAuthority -> grantedAuthority
				.getAuthority()
				.replaceAll(MemberContext.ROLE, ""))
			.toList();

		return jwtProvider.generate((String) authenticate.getPrincipal(), roles);
	}

	public JWTResponse refresh(final String refreshToken) {
		jwtProvider.validationRefreshToken(refreshToken);
		String email = jwtProvider.getIssuer(refreshToken);
		val loginAuth = memberRepo.findLoginAuthByEmail(Email.from(email))
			.orElseThrow(MemberNotFoundException::new);
		return jwtProvider.generate(email, List.of(loginAuth.roleType().name()));
	}
}
