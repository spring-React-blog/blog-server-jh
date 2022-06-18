package me.jojiapp.blogserverjh.global.security.service;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.exception.*;
import me.jojiapp.blogserverjh.domain.member.repo.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.security.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

	private final MemberRepo memberRepo;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		return memberRepo.findLoginAuthByEmail(Email.from(email))
				.map(MemberContext::from)
				.orElseThrow(MemberNotFoundException::new);
	}
}
