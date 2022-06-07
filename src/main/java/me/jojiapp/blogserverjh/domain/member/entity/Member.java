package me.jojiapp.blogserverjh.domain.member.entity;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.domain.entity.*;

import javax.persistence.*;
import java.time.*;

/**
 * 회원 엔티티
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseLastModifiedBy {

	/**
	 * 회원 식별자 아이디
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 이메일
	 *
	 * @see Email
	 */
	@Embedded
	private Email email;

	/**
	 * 비밀번호
	 *
	 * @see Password
	 */
	@Embedded
	private Password password;

	/**
	 * 이름
	 *
	 * @see Name
	 */
	@Embedded
	private Name name;

	/**
	 * 닉네임
	 *
	 * @see Nickname
	 */
	@Embedded
	private Nickname nickname;

	/**
	 * 생년월일
	 *
	 * @see Birth
	 */
	@Embedded
	private Birth birth;

	/**
	 * 권한
	 *
	 * @see RoleType
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoleType role;


	/**
	 * 연관 관계 전용 생성자
	 *
	 * @param id 회원 식별자 아이디
	 */
	private Member(final Long id) {
		this.id = id;
	}

	/**
	 * 회원 엔티티 빌더
	 *
	 * @param id       회원 식별자 아이디
	 * @param email    이메일
	 * @param password 비밀번호 (인코딩)
	 * @param name     이름
	 * @param nickname 닉네임
	 * @param birth    생일
	 * @param role     권한
	 *
	 * @see RoleType 권한
	 */
	@Builder
	@Deprecated
	public Member(
			final Long id,
			final String email,
			final String password,
			final String name,
			final String nickname,
			final LocalDateTime birth,
			final RoleType role
	) {
		this.id = id;
		this.email = Email.of(email);
		this.password = Password.of(password);
		this.name = Name.of(name);
		this.nickname = Nickname.of(nickname);
		this.birth = Birth.of(birth);
		this.role = role;
	}

	@Override
	public BaseEntity relation(Long id) {
		return new Member(id);
	}
}
