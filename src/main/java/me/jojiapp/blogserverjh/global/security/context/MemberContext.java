package me.jojiapp.blogserverjh.global.security.context;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

/**
 * Member UserDetails
 */
@ToString
public final class MemberContext extends User {

	/**
	 * 시큐리티 권한 Prefix
	 * <p>
	 * 시큐리티는 권한앞에 ROLE_가 붙어있어야 한다.
	 */
	private static final String ROLE = "ROLE_";

	/**
	 * 회원 고유 아이디
	 */
	private final Long id;

	/**
	 * @param memberAuth 인증에 필요한 회원 정보
	 *
	 * @see MemberAuth
	 */
	private MemberContext(final MemberAuth memberAuth) {
		super(memberAuth.email().getEmail(),
				memberAuth.password().getPassword(),
				parseAuthorities(memberAuth.roleType()));
		this.id = memberAuth.id();
	}

	/**
	 * @param memberAuth 인증에 필요한 회원 정보
	 *
	 * @return Member UserDetails
	 *
	 * @see MemberContext
	 */
	public static MemberContext of(final MemberAuth memberAuth) {
		return new MemberContext(memberAuth);
	}

	/**
	 * 단건 권한을 받아 시큐리티에서 사용되는 권한으로 변환
	 *
	 * @param roleType 권한
	 *
	 * @return List GrantedAuthority
	 *
	 * @see GrantedAuthority
	 */
	public static List<GrantedAuthority> parseAuthorities(final RoleType roleType) {
		return List.of(getAuthority(roleType));
	}

	/**
	 * 권한을 받아 시큐리티에서 사용되는 권한을 생성
	 *
	 * @param roleType 권한
	 *
	 * @return SimpleGrantedAuthority
	 *
	 * @see SimpleGrantedAuthority
	 */
	private static SimpleGrantedAuthority getAuthority(RoleType roleType) {
		return new SimpleGrantedAuthority(ROLE + roleType);
	}

	/**
	 * 문자열로 된 권한을 받아 시큐리티에서 사용되는 권한을 생성
	 *
	 * @param role 문자열로 된 권한
	 *
	 * @return SimpleGrantedAuthority
	 *
	 * @see SimpleGrantedAuthority
	 */
	private static SimpleGrantedAuthority getAuthority(String role) {
		return new SimpleGrantedAuthority(ROLE + role);
	}

	/**
	 * 문자열로 된 권한 리스트를 받아 시큐리티에서 사용되는 권한으로 변환
	 *
	 * @param roles 문자열로 된 권한 리스트
	 *
	 * @return List GrantedAuthority
	 *
	 * @see GrantedAuthority
	 */
	public static List<? extends GrantedAuthority> parseAuthorities(final List<String> roles) {
		return roles.stream()
				.map(MemberContext::getAuthority)
				.toList();
	}

	/**
	 * 회원 고유 아이디를 반환
	 *
	 * @return 회원 고유 아이디
	 */
	public Long getId() {
		return id;
	}
}
