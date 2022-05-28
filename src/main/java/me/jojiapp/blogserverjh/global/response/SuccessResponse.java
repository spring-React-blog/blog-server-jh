package me.jojiapp.blogserverjh.global.response;

/**
 * 요청에 정상적으로 성공 했을 때 반환되는 Response
 *
 * @param success 성공 여부
 */
public record SuccessResponse(
		boolean success
) {}
