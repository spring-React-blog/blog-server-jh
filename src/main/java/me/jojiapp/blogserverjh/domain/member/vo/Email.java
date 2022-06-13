package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {

	@Column(nullable = false, unique = true)
	private String email;

	public static Email from(final String email) {
		return new Email(email);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Email email1 = (Email) o;
		return Objects.equals(email, email1.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
}
