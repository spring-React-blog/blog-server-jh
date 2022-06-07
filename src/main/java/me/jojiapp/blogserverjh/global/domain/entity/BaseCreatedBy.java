package me.jojiapp.blogserverjh.global.domain.entity;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.entity.*;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import java.time.*;

/**
 * 최초 생성자 및 시간 공통 엔티티
 *
 * @see BaseEntity
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseCreatedBy extends BaseEntity {

	/**
	 * 최초 생성자
	 * <p>
	 * 최초로 생성되는 시점에 자동으로 현재 회원 주입
	 */
	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	private Member member;

	/**
	 * 최초 생성 시간
	 * <p>
	 * 최초로 생성되는 시점에 자동으로 시간 주입
	 */
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDateTime;
}
