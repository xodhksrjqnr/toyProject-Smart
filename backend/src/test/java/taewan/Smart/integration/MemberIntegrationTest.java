package taewan.Smart.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.dto.MemberCertificateDto;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.member.service.MemberCertificationService;
import taewan.Smart.domain.member.service.MemberService;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static taewan.Smart.fixture.MemberTestFixture.createPhoneNumber;
import static taewan.Smart.fixture.MemberTestFixture.getMemberSaveDtoList;
import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.utils.PropertyUtil.getClientAddress;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class MemberIntegrationTest {

    @Autowired private MemberCertificationService memberCertificationService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;

    //MemberServiceTest

    @Test
    void 회원_저장_테스트() {
        //given
        MemberSaveDto dto = getMemberSaveDtoList().get(0);

        //when
        memberService.save(dto);

        //then
        assertEquals(memberRepository.count(), 1);
    }

    @Test
    void 닉네임이_중복된_회원_저장_테스트() {
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
    void memberId로_회원_조회_테스트() {
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
    void 없는_memberId로_회원_조회_테스트() {
        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne(1L));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    @Test
    void email로_회원_조회_테스트() {
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
    void 없는_email로_회원_조회_테스트() {
        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.findOne("invalid"));
        assertEquals(ex.getMessage(), MEMBER_EMAIL_NOT_FOUND.exception().getMessage());
    }

    @Test
    void 닉네임과_패스워드로_회원_조회_테스트() {
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
    void 없는_닉네임과_패스워드로_회원_조회_테스트() {
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
    void 닉네임과_없는_패스워드로_회원_조회_테스트() {
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
    void 없는_닉네임과_없는_패스워드로_회원_조회_테스트() {
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
    void 회원_수정_테스트() {
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
        assertEquals(updated.toString(), found.toString());
    }

    @Test
    void 없는_회원_수정_테스트() {
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
    void 비밀번호를_비워둔_회원_수정_테스트() {
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

        // then
        assertEquals(found.get().getPassword(), saveDto.getPassword());
    }

    @Test
    void 수정하려는_닉네임이_중복된_회원_수정_테스트() {
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
    void 회원_탈퇴_테스트() {
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
    void 없는_회원_탈퇴_테스트() {
        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberService.delete(1L));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    //MemberCertificationTest

    @Test
    void 이메일_인증_테스트() {
        //given
        String email = "test@test.com";

        //when
        MemberCertificateDto result = memberCertificationService.findEmail(email);

        //then
        assertEquals(result.getMessage(), "[Smart] 회원가입 이메일 인증 안내 메일입니다.");
        assertEquals(result.getText(), getClientAddress() + "signup");
    }

    @Test
    void 중복된_이메일_인증_테스트() {
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
    void 이메일로_회원_아이디_찾기_테스트() {
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
    void 없는_이메일로_회원_아이디_찾기_테스트() {
        //given
        String email = "test.@test.com";

        //when
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(email));
        assertEquals(ex.getMessage(), MEMBER_EMAIL_NOT_FOUND.exception().getMessage());
    }

    @Test
    void 이메일과_닉네임으로_회원_아이디_찾기_테스트() {
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
    void 없는_이메일과_닉네임으로_회원_아이디_찾기_테스트() {
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
    void 이메일과_없는_닉네임으로_회원_아이디_찾기_테스트() {
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
    void 없는_이메일과_없는_닉네임으로_회원_아이디_찾기_테스트() {
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
