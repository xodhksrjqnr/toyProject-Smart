package taewan.Smart.unit.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static taewan.Smart.unit.member.MemberFixture.createMember;
import static taewan.Smart.unit.member.MemberFixture.createMembers;

@DataJpaTest
@EnableJpaRepositories(basePackages = "taewan.Smart.domain.member.repository")
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    void 회원_저장() {
        //given
        Member member = createMember();

        //when
        Member saved = memberRepository.save(member);

        //then
        assertThat(member.getNickName()).isEqualTo(saved.getNickName());
        assertThat(member.getEmail()).isEqualTo(saved.getEmail());
        assertThat(member.getPassword()).isEqualTo(saved.getPassword());
        assertThat(member.getPhoneNumber()).isEqualTo(saved.getPhoneNumber());
        assertThat(member.getBirthday()).isEqualTo(saved.getBirthday());
    }

    @Test
    void 회원_단일조회() {
        //given
        Member saved = memberRepository.save(createMember());

        //when
        Member found = memberRepository.findById(saved.getMemberId()).orElseThrow();

        //then
        assertThat(found.toString()).isEqualTo(saved.toString());
    }

    @Test
    void 없는_회원_단일조회() {
        //given //when
        Optional<Member> found = memberRepository.findById(1L);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 회원_전체조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when
        List<Member> found = memberRepository.findAll();

        //then
        assertEquals(found.size(), saved.size());
        for (int i = 0; i < saved.size(); i++)
            assertEquals(found.get(i).toString(), saved.get(i).toString());
    }

    @Test
    void 닉네임_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when //then
        for (Member member : saved) {
            Member found = memberRepository.findByNickName(member.getNickName()).orElseThrow();

            assertEquals(found.toString(), member.toString());
        }
    }

    @Test
    void 없는_닉네임_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when
        Optional<Member> found = memberRepository.findByNickName("invalidNickName");

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 이메일_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when //then
        for (Member member : saved) {
            Member found = memberRepository.findByEmail(member.getEmail()).orElseThrow();

            assertEquals(found.toString(), member.toString());
        }
    }

    @Test
    void 없는_이메일_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when
        Optional<Member> found = memberRepository.findByEmail("invalidEmail");

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 닉네임과_이메일이_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = saved.get(0).getNickName();
        String email = saved.get(0).getEmail();

        //when
        Optional<Member> found = memberRepository.findByNickNameAndEmail(nickName, email);

        //then
        assertThat(found).isNotEmpty();
        assertEquals(found.get().toString(), saved.get(0).toString());
    }

    @Test
    void 없는_닉네임과_이메일이_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = "invalidNickName";
        String email = saved.get(0).getEmail();

        //when
        Optional<Member> found = memberRepository.findByNickNameAndEmail(nickName, email);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 닉네임과_없는_이메일이_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = saved.get(0).getNickName();
        String email = "invalidEmail";

        //when
        Optional<Member> found = memberRepository.findByNickNameAndEmail(nickName, email);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 없는_닉네임과_없는_이메일이_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = "invalidNickName";
        String email = "invalidEmail";

        //when
        Optional<Member> found = memberRepository.findByNickNameAndEmail(nickName, email);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 닉네임과_패스워드가_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = saved.get(0).getNickName();
        String password = saved.get(0).getPassword();

        //when
        Optional<Member> found = memberRepository.findByNickNameAndPassword(nickName, password);

        //then
        assertThat(found).isNotEmpty();
        assertEquals(found.get().toString(), saved.get(0).toString());
    }

    @Test
    void 없는_닉네임과_패스워드가_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = "invalidNickName";
        String password = saved.get(0).getPassword();

        //when
        Optional<Member> found = memberRepository.findByNickNameAndPassword(nickName, password);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 닉네임과_없는_패스워드가_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = saved.get(0).getNickName();
        String password = "invalidPassword";

        //when
        Optional<Member> found = memberRepository.findByNickNameAndPassword(nickName, password);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    void 없는_닉네임과_없는_패스워드가_일치하는_회원_조회() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = "invalidNickName";
        String password = "invalidPassword";

        //when
        Optional<Member> found = memberRepository.findByNickNameAndPassword(nickName, password);

        //then
        assertThat(found).isEmpty();
    }


    @Test
    void 회원_삭제() {
        //given
        Member saved = memberRepository.save(createMember());

        //when
        memberRepository.deleteById(saved.getMemberId());

        //then
        assertEquals(memberRepository.count(), 0);
        assertThat(memberRepository.findById(saved.getMemberId())).isEmpty();
    }
}