package me.jojiapp.blogserverjh.global.domain.entity;

import lombok.*;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import java.time.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class BaseLastModifiedBy extends BaseCreatedBy {

	@LastModifiedBy
	private Long lastModifiedBy;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime lastModifiedDateTime;
}
