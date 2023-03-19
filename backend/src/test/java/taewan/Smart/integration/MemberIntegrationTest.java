package taewan.Smart.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.dto.MemberCertificateDto;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.member.service.MemberCertificationService;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.fixture.MemberTestFixture;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static taewan.Smart.fixture.MemberTestFixture.createPhoneNumber;
import static taewan.Smart.fixture.MemberTestFixture.getMemberSaveDtoList;
import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.util.PropertyUtils.getClientAddress;

@SpringBootTest
@Transactional
public class MemberIntegrationTest {

    @Autowired private MemberCertificationService memberCertificationService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    //MemberServiceTest

    @Test
    @DisplayName("회원 저장 테스트")
    void save() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);

        //when
        memberService.save(dto);

        //then
        assertEquals(memberRepository.count(), 1);
    }

    @Test
    @DisplayName("회원가입시 회원 닉네임이 중복된 경우 DuplicateKeyException가 발생")
    void save_duplicate_nickName() {
        //given
        MemberSaveDto dto1 = getMemberSaveDtoList().get(0);
        MemberSaveDto dto2 = MemberSaveDto.builder()
                .nickName(dto1.getNickName())
                .email("test@test.com")
                .password("test1234")
                .birthday(LocalDate.now())
                .phoneNumber("010-1234-1234")
                .build();

        //when //then
        memberService.save(dto1);
        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class, () -> memberService.save(dto2));
        assertEquals(ex.getMessage(), MEMBER_NICKNAME_DUPLICATE.exception().getMessage());
        assertEquals(memberRepository.count(), 1);
    }

    @Test
    @DisplayName("회원 기본키를 이용한 회원 조회 테스트")
    void findOne_valid_memberId() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);

        //when
        Long memberId = memberService.save(dto);
        MemberInfoDto found = memberService.findOne(memberId);

        //then
        assertEquals(found.getNickName(), dto.getNickName());
        assertEquals(found.getEmail(), dto.getEmail());
        assertEquals(found.getBirthday(), dto.getBirthday());
        assertEquals(found.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    @DisplayName("존재하지 않는 회원 기본키를 이용해 회원을 조회하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_memberId() {
        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne(1L));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("이메일을 이용한 회원 조회 테스트")
    void findOne_valid_email() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);

        //when
        memberService.save(dto);
        MemberInfoDto found = memberService.findOne(dto.getEmail());

        //then
        assertEquals(found.getNickName(), dto.getNickName());
        assertEquals(found.getEmail(), dto.getEmail());
        assertEquals(found.getBirthday(), dto.getBirthday());
        assertEquals(found.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    @DisplayName("존재하지 않는 이메일을 이용해 회원을 조회하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_email() {
        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne("invalid"));
        assertEquals(ex.getMessage(), MEMBER_EMAIL_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("닉네임과 패스워드를 이용한 회원 조회 테스트")
    void findOne_valid_nickName_valid_password() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);

        //when
        memberService.save(dto);
        MemberInfoDto found = memberService.findOne(dto.getNickName(), dto.getPassword());

        //then
        assertEquals(found.getNickName(), dto.getNickName());
        assertEquals(found.getEmail(), dto.getEmail());
        assertEquals(found.getBirthday(), dto.getBirthday());
        assertEquals(found.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    @DisplayName("등록되지 않는 닉네임과 등록된 패스워드를 이용해 회원을 조회하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_nickName_valid_password() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        String nickName = "invalidNickName";
        String password = dto.getPassword();

        //when
        memberService.save(dto);

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne(nickName, password));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("등록된 닉네임과 등록되지 않은 패스워드를 이용해 회원을 조회하는 경우 NoSuchElementException가 발생")
    void findOne_valid_nickName_invalid_password() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        String nickName = dto.getNickName();
        String password = "invalidPW";

        //when
        memberService.save(dto);

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne(nickName, password));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("닉네임과 패스워드 모두 등록되지 않았을 때 회원을 조회하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_all() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        String nickName = "invalidNickName";
        String password = "invalidPW";

        //when
        memberService.save(dto);

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne(nickName, password));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void update() {
        //given
        MemberSaveDto saveDto = getMemberSaveDtoList().get(0);
        Long memberId = memberService.save(saveDto);
        MemberUpdateDto updateDto = MemberUpdateDto.builder()
                .memberId(memberId)
                .nickName("test")
                .password("test1234")
                .email("test@test.com")
                .birthday(LocalDate.now().minusDays(1))
                .phoneNumber(createPhoneNumber())
                .build();

        //when
        MemberInfoDto updated = memberService.update(updateDto);
        MemberInfoDto found = memberService.findOne(memberId);

        //then
        assertEquals(
                MemberTestFixture.toString(updated),
                MemberTestFixture.toString(found)
        );
    }

    @Test
    @DisplayName("등록되지 않은 회원 기본키를 이용해 회원 정보를 수정하는 경우 NoSuchElementException가 발생")
    void update_invalid_memberId() {
        //given
        MemberUpdateDto updateDto = MemberUpdateDto.builder()
                .memberId(1L)
                .nickName("test")
                .email("test@test.com")
                .password("test1234")
                .birthday(LocalDate.now().minusDays(1))
                .phoneNumber(createPhoneNumber())
                .build();

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.update(updateDto));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("회원 정보 수정 시 비밀번호가 비워져 있는 경우 비밀번호를 수정하지 않는다고 판단하여 이전 비밀번호를 그대로 사용")
    void update_blank_password() {
        //given
        MemberSaveDto saveDto = getMemberSaveDtoList().get(0);
        Long memberId = memberService.save(saveDto);
        MemberUpdateDto updateDto = MemberUpdateDto.builder()
                .memberId(memberId)
                .nickName("test")
                .email("test@test.com")
                .password("")
                .birthday(LocalDate.now().minusDays(1))
                .phoneNumber(createPhoneNumber())
                .build();

        //when
        Optional<Member> found = memberRepository.findById(memberId);
        found.get().updateMember(updateDto);

        // then
        assertEquals(found.get().getPassword(), saveDto.getPassword());
    }

    @Test
    @DisplayName("회원 정보 수정 시 변경하려는 회원 닉네임이 중복된 경우 DuplicateKeyException가 발생")
    void update_duplicate_nickName() {
        //given
        MemberSaveDto saveDto1 = getMemberSaveDtoList().get(0);
        MemberSaveDto saveDto2 = getMemberSaveDtoList().get(1);
        Long memberId1 = memberService.save(saveDto1);
        memberService.save(saveDto2);
        MemberUpdateDto updateDto = MemberUpdateDto.builder()
                .memberId(memberId1)
                .nickName(saveDto2.getNickName())
                .email("test@test.com")
                .birthday(LocalDate.now().minusDays(1))
                .phoneNumber(createPhoneNumber())
                .build();

        //when //then
        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> memberService.update(updateDto));
        assertEquals(ex.getMessage(), MEMBER_NICKNAME_DUPLICATE.exception().getMessage());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void delete() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);

        //when
        Long memberId = memberService.save(dto);

        //then
        assertEquals(memberRepository.count(), 1);
        memberService.delete(memberId);
        assertEquals(memberRepository.count(), 0);
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne(memberId));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 회원 기본키를 이용해 회원 탈퇴를 시도하는 경우 NoSuchElementException가 발생")
    void delete_invalid_memberId() {
        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.delete(1L));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    //MemberCertificationTest

    @Test
    @DisplayName("인증받으려는 이메일이 등록되어있지 않은 사용 가능한 이메일인 경우 회원가입 링크를 담은 메시지를 담아 반환")
    void findEmail() {
        //given
        String email = "test@test.com";

        //when
        MemberCertificateDto result = memberCertificationService.findEmail(email);

        //then
        assertEquals(result.getMessage(), "[Smart] 회원가입 이메일 인증 안내 메일입니다.");
        assertEquals(result.getText(), getClientAddress() + "signup");
    }

    @Test
    @DisplayName("인증받으려는 이메일이 이미 등록된 경우 DuplicateKeyException가 발생")
    void findEmail_duplicate_email() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        memberService.save(dto);
        String email = dto.getEmail();

        //when
        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class,
                () -> memberCertificationService.findEmail(email));
        assertEquals(ex.getMessage(), MEMBER_EMAIL_DUPLICATE.exception().getMessage());
    }

    @Test
    @DisplayName("이메일을 이용한 회원 닉네임 조회 테스트")
    void findMember() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        memberService.save(dto);

        //when
        MemberCertificateDto result = memberCertificationService.findMember(dto.getEmail());

        //then
        assertEquals(result.getMessage(), "[Smart] 회원 아이디 찾기 안내 메일입니다.");
        assertEquals(result.getText(), "회원님의 아이디는 \"" + dto.getNickName() + "\"입니다.");
    }

    @Test
    @DisplayName("등록되지 않은 이메일을 이용해 회원 닉네임을 조회하는 경우 NoSuchElementException가 발생")
    void findMember_invalid_email() {
        //given
        String email = "test.@test.com";

        //when
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(email));
        assertEquals(ex.getMessage(), MEMBER_EMAIL_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("이메일과 닉네임을 이용해 회원 패스워드를 조회하는 경우 임시 비밀번호를 생성")
    void findMember_valid_email_valid_nickName() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        Long memberId = memberService.save(dto);
        String email = dto.getEmail();
        String nickName = dto.getNickName();

        //when
        MemberCertificateDto result = memberCertificationService.findMember(email, nickName);
        Member member = memberRepository.findById(memberId).get();

        //then
        assertEquals(result.getMessage(), "[Smart] 회원 비밀번호 찾기 안내 메일입니다.");
        assertThat(dto.getPassword().equals(member.getPassword())).isFalse();
    }

    @Test
    @DisplayName("등록되지 않은 이메일과 등록된 닉네임으로 회원 패스워드를 조회하는 경우 NoSuchElementException가 발생")
    void findMember_invalid_email_valid_nickName() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        Long memberId = memberService.save(dto);
        String password = memberRepository.findById(memberId).get().getPassword();
        String email = "invalidEmail";
        String nickName = dto.getNickName();

        //when
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(nickName, email));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("등록된 이메일과 등록되지 않은 닉네임으로 회원 패스워드를 조회하는 경우 NoSuchElementException가 발생")
    void findMember_valid_email_invalid_nickName() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        Long memberId = memberService.save(dto);
        String password = memberRepository.findById(memberId).get().getPassword();
        String email = dto.getEmail();
        String nickName = "invalidNickName";

        //when
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(nickName, email));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("이메일과 닉네임 모두 등록되지 않았을 때 회원 패스워드를 조회하는 경우 NoSuchElementException가 발생")
    void findMember_invalid_all() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);
        Long memberId = memberService.save(dto);
        String password = memberRepository.findById(memberId).get().getPassword();
        String email = "invalidEmail";
        String nickName = "invalidNickName";

        //when
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(nickName, email));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }
}
