package me.jojiapp.blogserverjh.global.domain.entity;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.entity.*;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import java.time.*;

/**
 * 마지막 수정자 및 시간 공통 엔티티
 *
 * @see BaseCreatedBy
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class BaseLastModifiedBy extends BaseCreatedBy {

	/**
	 * 마지막 수정자
	 * <p>
	 * 수정되는 시점에 자동으로 현재 회원 주입
	 */
	@LastModifiedBy
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	/**
	 * 마지막 수정 시간
	 * <p>
	 * 수정되는 시점의 시간 자동 주입
	 */
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime lastModifiedDateTime;
}
