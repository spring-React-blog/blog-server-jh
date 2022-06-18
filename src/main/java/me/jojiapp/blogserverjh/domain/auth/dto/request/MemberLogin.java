package me.jojiapp.blogserverjh.domain.auth.dto.request;

import javax.validation.constraints.*;

public record MemberLogin(
		@NotBlank
		@Email
		String email,

		@NotBlank
		String password
) {}
