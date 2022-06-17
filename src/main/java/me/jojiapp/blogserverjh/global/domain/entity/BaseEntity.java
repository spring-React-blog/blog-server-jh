package me.jojiapp.blogserverjh.global.domain.entity;

import lombok.*;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import java.time.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

	@CreatedBy
	@Column(updatable = false)
	private String createdBy;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDateTime;

	@LastModifiedBy
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime lastModifiedDateTime;
}
