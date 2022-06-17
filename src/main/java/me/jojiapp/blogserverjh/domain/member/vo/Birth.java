package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Birth {

	@Column(nullable = false)
	private LocalDateTime birth;

	public static Birth from(final LocalDateTime birth) {
		return new Birth(birth);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Birth birth1 = (Birth) o;
		return Objects.equals(birth, birth1.birth);
	}

	@Override
	public int hashCode() {
		return Objects.hash(birth);
	}
}
