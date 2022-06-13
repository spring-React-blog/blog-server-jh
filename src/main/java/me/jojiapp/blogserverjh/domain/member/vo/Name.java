package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Name {

	@Column(nullable = false)
	private String name;

	public static Name from(final String name) {
		return new Name(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Name name1 = (Name) o;
		return Objects.equals(name, name1.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
