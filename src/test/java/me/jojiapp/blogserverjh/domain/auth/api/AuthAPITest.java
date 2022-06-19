package me.jojiapp.blogserverjh.domain.auth.api;

import lombok.*;
import me.jojiapp.blogserverjh.domain.auth.dto.request.*;
import me.jojiapp.blogserverjh.domain.auth.service.*;
import me.jojiapp.blogserverjh.domain.member.exception.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.jwt.*;
import me.jojiapp.blogserverjh.global.security.exception.*;
import me.jojiapp.blogserverjh.support.test.*;
import org.hamcrest.core.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;

import java.util.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthAPITest extends APITest {

	private static final String BASE_DOCUMENT = "api/public/auth";
	private static final String BASE_API = "/" + BASE_DOCUMENT;
	@MockBean
	private AuthService authService;
	@Autowired
	private JWTProvider jwtProvider;

	@Nested
	class Login {

		private static final String LOGIN_API = BASE_API + "/login";
		private static final String LOGIN_DOCUMENT = BASE_DOCUMENT + "/login";

		@Test
		@DisplayName("이메일과 비밀번호를 입력하여 일치하면 JWT를 반환한다")
		void test1() throws Exception {
			// Given
			val memberLogin = new MemberLogin(
					EMAIL,
					PASSWORD
			);
			val jwtResponse = jwtProvider.generate(EMAIL, List.of(RoleType.USER.name()));
			given(authService.login(memberLogin)).willReturn(jwtResponse);

			// When
			mockMvc.perform(post(LOGIN_API)
							.contentType(APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(memberLogin)))
					// Then
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.body.accessToken").value(jwtResponse.accessToken()))
					.andExpect(cookie().value("refreshToken", jwtResponse.refreshToken()))
					// Document
					.andDo(
							document(
									LOGIN_DOCUMENT,
									requestHeaders(
											headerWithName(HttpHeaders.CONTENT_TYPE).description(APPLICATION_JSON)
									),

									requestFields(
											fieldWithPath("email").description("이메일").type(STRING),
											fieldWithPath("password").description("비밀번호").type(STRING)
									),
									responseHeaders(
											headerWithName(HttpHeaders.CONTENT_TYPE).description(APPLICATION_JSON),
											headerWithName(HttpHeaders.SET_COOKIE).description("리프래쉬 토큰")
									),
									responseFields(
											fieldWithPath("message").description("메세지").type(STRING),
											fieldWithPath("body.accessToken").description("액세스 토큰").type(STRING)
									)
							)
					);
		}

		@Test
		@DisplayName("이메일과 비밀번호가 바인딩에 실패하면 400 코드를 반환한다")
		void loginBindingError() throws Exception {
			// Given
			val memberLogin = new MemberLogin(
					" ",
					" "
			);

			// When
			val document = LOGIN_DOCUMENT + "/error/binding";
			mockMvc.perform(post(LOGIN_API)
							.contentType(APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(memberLogin)))
					// Then
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.body").value(IsNull.nullValue()))
					.andExpect(jsonPath("$.message").isString())
					// Document
					.andDo(
							document(
									document,
									responseHeaders(
											headerWithName(HttpHeaders.CONTENT_TYPE).description(APPLICATION_JSON)
									),
									responseFields(
											fieldWithPath("message").description("메세지").type(STRING),
											fieldWithPath("body").description("데이터").type(NULL)
									)
							)
					);
		}

		@Test
		@DisplayName("비밀번호가 일치하지 않을 경우 PasswordNotMatchException과 함께 400 코드를 반환한다")
		void loginPasswordNotMatchException() throws Exception {
			// Given
			val memberLogin = new MemberLogin(
					EMAIL,
					PASSWORD
			);
			val exception = new PasswordNotMatchException();
			given(authService.login(memberLogin)).willThrow(exception);

			// When
			val document = LOGIN_DOCUMENT + "/error/password-not-match";
			mockMvc.perform(post(LOGIN_API)
							.contentType(APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(memberLogin)))
					// Then
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.body").value(IsNull.nullValue()))
					.andExpect(jsonPath("$.message").value(exception.getMessage()))
					// Document
					.andDo(
							document(
									document,
									responseHeaders(
											headerWithName(HttpHeaders.CONTENT_TYPE).description(APPLICATION_JSON)
									),
									responseFields(
											fieldWithPath("message").description("메세지").type(STRING),
											fieldWithPath("body").description("데이터").type(NULL)
									)
							)
					);
		}

		@Test
		@DisplayName("존재하지 않은 이메일일 경우 MemberNotFoundException과 함께 400 코드를 반환한다")
		void loginMemberNotFoundException() throws Exception {
			// Given
			val memberLogin = new MemberLogin(
					EMAIL,
					PASSWORD
			);
			val exception = new MemberNotFoundException();
			given(authService.login(memberLogin)).willThrow(exception);

			val document = LOGIN_DOCUMENT + "/error/member-not-found";
			mockMvc.perform(post(LOGIN_API)
							.contentType(APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(memberLogin)))
					// Then
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.body").value(IsNull.nullValue()))
					.andExpect(jsonPath("$.message").value(exception.getMessage()))
					// Document
					.andDo(
							document(
									document,
									responseHeaders(
											headerWithName(HttpHeaders.CONTENT_TYPE).description(APPLICATION_JSON)
									),
									responseFields(
											fieldWithPath("message").description("메세지").type(STRING),
											fieldWithPath("body").description("데이터").type(NULL)
									)
							)
					);
		}
	}

}
