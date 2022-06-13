package me.jojiapp.blogserverjh.global.domain.entity;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

	public abstract BaseEntity relation(final Long id);

}
