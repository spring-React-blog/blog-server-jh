package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Nickname {

	@Column(nullable = false)
	private String nickname;

	public static Nickname from(final String nickname) {
		return new Nickname(nickname);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Nickname nickname1 = (Nickname) o;
		return Objects.equals(nickname, nickname1.nickname);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nickname);
	}
}
