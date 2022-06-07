package me.jojiapp.blogserverjh.domain.member.vo;

import lombok.*;

import javax.persistence.*;
import java.time.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Birth {

	/**
	 * 생년월일
	 */
	@Column(nullable = false)
	private LocalDateTime birth;

	/**
	 * 생년월일 생성자 팩토리 메소드
	 *
	 * @param birth 생년월일
	 *
	 * @return 생년월일 VO
	 */
	public static Birth of(final LocalDateTime birth) {
		return new Birth(birth);
	}
}
