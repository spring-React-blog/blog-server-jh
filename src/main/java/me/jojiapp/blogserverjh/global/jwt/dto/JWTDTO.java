package me.jojiapp.blogserverjh.global.jwt.dto;

/**
 * JWT DTO
 *
 * @param accessToken  JWT로 인코딩 된 Access Token
 * @param refreshToken JWT로 인코딩 된 Refresh Token
 */
public record JWTDTO(
		String accessToken,
		String refreshToken
) {}
