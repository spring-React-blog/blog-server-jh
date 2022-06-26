package me.jojiapp.blogserverjh.domain.member.repo;

import me.jojiapp.blogserverjh.domain.member.entity.*;
import org.springframework.data.jpa.repository.*;

public interface MemberRepo extends JpaRepository<Member, Long>, MemberCustomRepo {

}
