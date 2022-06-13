package me.jojiapp.blogserverjh.domain.member.entity;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.domain.entity.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseLastModifiedBy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	@Embedded
	private Name name;

	@Embedded
	private Nickname nickname;

	@Embedded
	private Birth birth;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoleType role;


	private Member(final Long id) {
		this.id = id;
	}

	@Builder
	private Member(
			final Long id,
			final String email,
			final String password,
			final String name,
			final String nickname,
			final LocalDateTime birth,
			final RoleType role
	) {
		this.id = id;
		this.email = Email.from(email);
		this.password = Password.from(password);
		this.name = Name.from(name);
		this.nickname = Nickname.from(nickname);
		this.birth = Birth.from(birth);
		this.role = role;
	}

	@Override
	public BaseEntity relation(Long id) {
		return new Member(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Member member = (Member) o;
		return Objects.equals(id, member.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
