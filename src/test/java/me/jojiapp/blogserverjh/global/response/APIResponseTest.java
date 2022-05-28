package me.jojiapp.blogserverjh.global.response;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class APIResponseTest {

	@Test
	@DisplayName("success에 파라미터가 없으면 body.success true를 반환한다")
	void successParameterEmpty() throws Exception {
		// When
		APIResponse<SuccessResponse> actual = APIResponse.success();

		// Then
		assertThat(actual.body().success()).isTrue();
		assertThat(actual.message()).isEmpty();
	}

	@Test
	@DisplayName("success에 파라미터가 있으면, body의 값으로 할당된다")
	void successParameter() throws Exception {
		// Given
		Map<String, String> name = Map.of("name", "test");

		// When
		APIResponse<Map<String, String>> actual = APIResponse.success(name);

		// Then
		assertThat(actual.body().get("name")).isEqualTo("test");
		assertThat(actual.message()).isEmpty();
	}

	@Test
	@DisplayName("error가 발생하면 message에 값이 할당된다")
	void error() throws Exception {
		// Given
		String errorMessage = "에러메세지";

		// When
		APIResponse<Void> actual = APIResponse.error(errorMessage);

		// Then
		assertThat(actual.body()).isNull();
		assertThat(actual.message()).isEqualTo(errorMessage);
	}

}
