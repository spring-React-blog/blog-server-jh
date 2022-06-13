package me.jojiapp.blogserverjh.global.domain.entity;

import lombok.*;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import java.time.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseCreatedBy extends BaseEntity {

	@CreatedBy
	@Column(updatable = false)
	private Long createdBy;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDateTime;
}
