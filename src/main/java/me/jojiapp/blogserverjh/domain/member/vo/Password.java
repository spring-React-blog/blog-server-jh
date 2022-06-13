package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;
import org.springframework.security.crypto.password.*;

import javax.persistence.*;
import java.util.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

	@Column(nullable = false, length = 500)
	private String password;

	public static Password from(final String password) {
		return new Password(password);
	}

	public static Password create(final String password, final PasswordEncoder passwordEncoder) {
		return from(passwordEncoder.encode(password));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Password password1 = (Password) o;
		return Objects.equals(password, password1.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(password);
	}
}
