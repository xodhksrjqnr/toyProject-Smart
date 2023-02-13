package taewan.Smart.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taewan.Smart.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickName(String memberId);
    Optional<Member> findByNickNameAndEmail(String memberId, String email);
    Optional<Member> findByNickNameAndPassword(String memberId, String password);
    Optional<Member> findByEmail(String email);
}
