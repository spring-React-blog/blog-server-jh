package me.jojiapp.blogserverjh.global.security.context;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;

public class MemberContextGiven {

	public static final String ROLE_USER = "ROLE_%s".formatted(RoleType.USER);

	public static MemberContext givenMemberContext() {
		val accessTokenResponse = new LoginAuth(
				Email.from(EMAIL),
				Password.from(PASSWORD),
				RoleType.USER
		);
		// When
		return MemberContext.from(accessTokenResponse);
	}
}
