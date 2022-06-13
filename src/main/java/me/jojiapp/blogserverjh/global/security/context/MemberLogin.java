package me.jojiapp.blogserverjh.global.security.context;

import me.jojiapp.blogserverjh.domain.member.vo.*;

public record MemberLogin(
		Long id,
		Email email,
		Password password,
		RoleType roleType
) {}
