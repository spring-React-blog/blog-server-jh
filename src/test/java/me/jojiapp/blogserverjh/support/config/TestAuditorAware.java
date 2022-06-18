package me.jojiapp.blogserverjh.support.config;

import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;

import java.util.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;

@TestConfiguration
public class TestAuditorAware implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(EMAIL);
	}
}
