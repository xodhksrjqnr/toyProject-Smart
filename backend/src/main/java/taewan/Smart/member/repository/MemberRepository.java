package taewan.Smart.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);
}
