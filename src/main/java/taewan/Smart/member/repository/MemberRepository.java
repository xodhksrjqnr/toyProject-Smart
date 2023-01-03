package taewan.Smart.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
