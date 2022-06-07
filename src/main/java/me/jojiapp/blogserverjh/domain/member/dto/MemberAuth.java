package me.jojiapp.blogserverjh.domain.member.dto;

import me.jojiapp.blogserverjh.domain.member.vo.*;

/**
 * 회원 인증에 필요한 정보
 *
 * @param id       회원 고유 아이디
 * @param email    이메일
 * @param password 비밀번호
 * @param roleType 권한
 *
 * @see Email
 * @see Password
 * @see RoleType
 */
public record MemberAuth(
		Long id,
		Email email,
		Password password,
		RoleType roleType
) {}
