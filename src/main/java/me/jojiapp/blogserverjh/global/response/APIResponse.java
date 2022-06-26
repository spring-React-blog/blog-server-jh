package me.jojiapp.blogserverjh.global.response;

public record APIResponse<T>(
	T body,
	String message
) {

	public static APIResponse<SuccessResponse> success() {
		return new APIResponse<>(new SuccessResponse(true), "");
	}

	public static <T> APIResponse<T> success(final T body) {
		return new APIResponse<>(body, "");
	}

	public static APIResponse<Void> error(final String message) {
		return new APIResponse<>(null, message);
	}
}
