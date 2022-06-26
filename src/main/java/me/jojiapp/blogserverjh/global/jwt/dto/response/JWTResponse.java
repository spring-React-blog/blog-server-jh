package me.jojiapp.blogserverjh.global.jwt.dto.response;

public record JWTResponse(
	String accessToken,
	String refreshToken
) {}
