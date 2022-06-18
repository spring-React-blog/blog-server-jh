package me.jojiapp.blogserverjh.global.security.provider;

import lombok.*;
import me.jojiapp.blogserverjh.global.security.exception.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;

@RequiredArgsConstructor
public class LoginAuthenticationProvider implements AuthenticationProvider {

	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;


	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		val email = (String) authentication.getPrincipal();
		val password = (String) authentication.getCredentials();
		val userDetails = userDetailsService.loadUserByUsername(email);

		validationMatchPassword(password, userDetails.getPassword());

		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
	}

	private void validationMatchPassword(final String rawPassword, final String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword))
			throw new PasswordNotMatchException();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}
}
