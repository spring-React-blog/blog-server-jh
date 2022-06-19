package me.jojiapp.blogserverjh.domain.auth.service;

import lombok.*;
import me.jojiapp.blogserverjh.domain.auth.dto.request.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.jwt.dto.response.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JWTProvider jwtProvider;

	public JWTResponse login(final MemberLogin memberLogin) {
		val authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(memberLogin.email(), memberLogin.password())
		);
		val roles = authenticate.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();

		return jwtProvider.generate((String) authenticate.getPrincipal(), roles);
	}
}
