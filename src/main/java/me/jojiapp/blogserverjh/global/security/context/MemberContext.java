package me.jojiapp.blogserverjh.global.security.context;

import me.jojiapp.blogserverjh.domain.member.vo.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

public final class MemberContext extends User {

	private static final String ROLE = "ROLE_";

	private MemberContext(final LoginAuth loginAuth) {
		super(loginAuth.email().getEmail(),
				loginAuth.password().getPassword(),
				parseAuthorities(loginAuth.roleType()));
	}

	public static MemberContext from(final LoginAuth loginAuth) {
		return new MemberContext(loginAuth);
	}

	public static List<GrantedAuthority> parseAuthorities(final RoleType roleType) {
		return List.of(getAuthority(roleType));
	}

	private static SimpleGrantedAuthority getAuthority(RoleType roleType) {
		return new SimpleGrantedAuthority(ROLE + roleType);
	}

	public static SimpleGrantedAuthority getAuthority(String role) {
		return new SimpleGrantedAuthority(ROLE + role);
	}

	public static List<? extends GrantedAuthority> parseAuthorities(final List<String> roles) {
		return roles.stream()
				.map(MemberContext::getAuthority)
				.toList();
	}

}
