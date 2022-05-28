package me.jojiapp.blogserverjh.global.response;

/**
 * 공통 API Response
 *
 * @param body    데이터
 * @param message 예외 메세지
 * @param <T>     데이터 타입
 */
public record APIResponse<T>(
		T body,
		String message
) {

	/**
	 * 요청을 정상적으로 처리했을 때, 반환하는 응답
	 *
	 * @return 성공 여부
	 *
	 * @see APIResponse
	 * @see SuccessResponse
	 */
	public static APIResponse<SuccessResponse> success() {
		return new APIResponse<>(new SuccessResponse(true), "");
	}

	/**
	 * 요청을 정상적으로 처리했을 때, 데이터를 담아 반환하는 응답
	 *
	 * @param body 데이터
	 * @param <T>  데이터 타입
	 *
	 * @return body에 응답 데이터를 담아 반환
	 *
	 * @see APIResponse
	 */
	public static <T> APIResponse<T> success(final T body) {
		return new APIResponse<>(body, "");
	}

	/**
	 * 예외가 발생했을 때, 반환하는 응답
	 *
	 * @param message 예외 메세지
	 *
	 * @return message에 예외 메세지를 담아 반환
	 *
	 * @see APIResponse
	 */
	public static APIResponse<Void> error(final String message) {
		return new APIResponse<>(null, message);
	}
}
