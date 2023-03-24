package taewan.Smart.unit.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static taewan.Smart.fixture.MemberTestFixture.createMembers;

@DataJpaTest
@EnableJpaRepositories(basePackages = "taewan.Smart.domain.member.repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("닉네임이 일치하는 회원 조회 테스트")
    void findByNickName() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when //then
        for (Member member : saved) {
            Member found = memberRepository.findByNickName(member.getNickName()).orElseThrow();

            assertEquals(found.toString(), member.toString());
        }
    }

    @Test
    @DisplayName("등록되지 않은 닉네임과 일치하는 회원 조회 테스트")
    void findByNickName_invalid_nickName() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when
        Optional<Member> found = memberRepository.findByNickName("invalidNickName");

        //then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("이메일이 일치하는 회원 조회 테스트")
    void findByEmail() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when //then
        for (Member member : saved) {
            Member found = memberRepository.findByEmail(member.getEmail()).orElseThrow();

            assertEquals(found.toString(), member.toString());
        }
    }

    @Test
    @DisplayName("등록되지 않은 이메일과 일치하는 회원 조회 테스트")
    void findByEmail_invalid_email() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());

        //when
        Optional<Member> found = memberRepository.findByEmail("invalidEmail");

        //then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("닉네임과 이메일이 모두 일치하는 회원 조회 테스트")
    void findByNickNameAndEmail() {
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
    @DisplayName("등록되지 않은 닉네임과 등록된 이메일과 일치하는 회원 조회 테스트")
    void findByNickNameAndEmail_invalid_nickName() {
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
    @DisplayName("등록된 닉네임과 등록되지 않은 이메일과 일치하는 회원 조회 테스트")
    void findByNickNameAndEmail_invalid_email() {
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
    @DisplayName("닉네임과 이메일 모두 등록되지 않은 경우의 회원 조회 테스트")
    void findByNickNameAndEmail_invalid_all() {
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
    @DisplayName("닉네임과 패스워드가 모두 일치하는 회원 조회 테스트")
    void findByNickNameAndPassword() {
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
    @DisplayName("등록되지 않은 닉네임과 등록된 패스워드와 일치하는 회원 조회 테스트")
    void findByNickNameAndPassword_invalid_nickName() {
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
    @DisplayName("등록된 닉네임과 등록되지 않은 패스워드와 일치하는 회원 조회 테스트")
    void findByNickNameAndPassword_invalid_password() {
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
    @DisplayName("닉네임과 패스워드 모두 등록되지 않은 경우의 회원 조회 테스트")
    void findByNickNameAndPassword_invalid_all() {
        //given
        List<Member> saved = memberRepository.saveAll(createMembers());
        String nickName = "invalidNickName";
        String password = "invalidPassword";

        //when
        Optional<Member> found = memberRepository.findByNickNameAndPassword(nickName, password);

        //then
        assertThat(found).isEmpty();
    }
}