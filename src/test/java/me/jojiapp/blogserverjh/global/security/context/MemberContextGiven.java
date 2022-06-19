package me.jojiapp.blogserverjh.global.security.context;

import me.jojiapp.blogserverjh.domain.member.vo.*;
import org.springframework.security.core.userdetails.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;

public class MemberContextGiven {

	public static final String ROLE_USER = "ROLE_%s".formatted(RoleType.USER);

	public static UserDetails givenMemberContext() {
		return MemberContext.from(givenLoginAuth());
	}

	public static LoginAuth givenLoginAuth() {
		return new LoginAuth(
				Email.from(EMAIL),
				Password.from(ENCODED_PASSWORD),
				RoleType.USER
		);
	}
}
