package me.jojiapp.blogserverjh.domain.member.repo;

import com.querydsl.core.types.*;
import com.querydsl.jpa.impl.*;
import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.security.context.*;

import java.util.*;

import static me.jojiapp.blogserverjh.domain.member.entity.QMember.*;

@RequiredArgsConstructor
public class MemberRepoImpl implements MemberCustomRepo {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<LoginAuth> findLoginAuthByEmail(final Email email) {
		return Optional.ofNullable(
				queryFactory.select(
								Projections.constructor(
										LoginAuth.class,
										member.email,
										member.password,
										member.role
								)
						)
						.from(member)
						.where(member.email.eq(email))
						.fetchOne()
		);
	}
}
