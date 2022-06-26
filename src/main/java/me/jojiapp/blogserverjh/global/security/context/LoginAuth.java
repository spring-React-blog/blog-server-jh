package me.jojiapp.blogserverjh.global.security.context;

import me.jojiapp.blogserverjh.domain.member.vo.*;

public record LoginAuth(
	Email email,
	Password password,
	RoleType roleType
) {}
