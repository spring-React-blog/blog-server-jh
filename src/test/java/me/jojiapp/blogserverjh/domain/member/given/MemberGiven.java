package me.jojiapp.blogserverjh.domain.member.given;

import me.jojiapp.blogserverjh.domain.member.entity.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;

import java.time.*;

public class MemberGiven {

	public static final String EMAIL = "email@gmail.com";
	public static final String PASSWORD = "password";

	public static final String NAME = "이름";
	public static final String NICKNAME = "닉네임";
	public static final LocalDateTime BIRTH = LocalDateTime.of(2022, 2, 2, 2, 2);

	public static Member givenMember() {
		return Member.builder()
				.id(null)
				.email(EMAIL)
				.password(PASSWORD)
				.name(NAME)
				.nickname(NICKNAME)
				.birth(BIRTH)
				.role(RoleType.USER)
				.build();
	}
}
