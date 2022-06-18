package me.jojiapp.blogserverjh.domain.member.repo;

import lombok.*;
import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.support.test.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;

import static me.jojiapp.blogserverjh.domain.member.given.MemberGiven.*;
import static org.assertj.core.api.Assertions.*;

@RepoTest
class MemberCustomRepoTest {

	@Autowired
	private MemberRepo memberRepo;

	@Nested
	class FindLoginAuthByEmail {
		@Test
		@DisplayName("이메일이 존재하면 AccessTokenResponse를 반환한다")
		void test1() throws Exception {
			// Given
			memberRepo.save(givenMember());
			val email = Email.from(EMAIL);

			// When
			val loginAUth = memberRepo.findLoginAuthByEmail(email);

			// Then
			assertThat(loginAUth.isPresent()).isTrue();

			val actual = loginAUth.get();
			assertThat(actual.email()).isEqualTo(Email.from(EMAIL));
			assertThat(actual.password()).isEqualTo(Password.from(PASSWORD));
			assertThat(actual.roleType()).isEqualTo(RoleType.USER);
		}

		@Test
		@DisplayName("이메일이 존재하지 않으면 empty를 반환한다")
		void test2() throws Exception {
			// Given
			Email email = Email.from("not@gmal.com");

			// When
			val actual = memberRepo.findLoginAuthByEmail(email);

			// Then
			assertThat(actual.isEmpty()).isTrue();
		}
	}

}
