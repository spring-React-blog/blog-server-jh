package me.jojiapp.blogserverjh.domain.member.repo;

import me.jojiapp.blogserverjh.domain.member.vo.*;
import me.jojiapp.blogserverjh.global.security.context.*;

import java.util.*;

public interface MemberCustomRepo {

	Optional<LoginAuth> findLoginAuthByEmail(final Email email);
}
