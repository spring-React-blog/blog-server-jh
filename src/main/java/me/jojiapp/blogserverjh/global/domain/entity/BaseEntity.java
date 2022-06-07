package me.jojiapp.blogserverjh.global.domain.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 기본 공통 엔티티
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

	/**
	 * 연관 관계 생성 전용 메소드
	 *
	 * @param id 엔티티 식별자 아이디
	 *
	 * @return 엔티티
	 */
	public abstract BaseEntity relation(final Long id);

}
