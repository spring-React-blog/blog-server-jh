package me.jojiapp.blogserverjh.global.security.context;

import me.jojiapp.blogserverjh.domain.member.vo.*;

/**
 * 회원 인증에 필요한 정보
 *
 * @param id       회원 고유 아이디
 * @param email    이메일
 * @param password 비밀번호
 * @param role     권한
 */
public record MemberAuth(
		Long id,
		Email email,
		Password password,
		Role role
) {}
