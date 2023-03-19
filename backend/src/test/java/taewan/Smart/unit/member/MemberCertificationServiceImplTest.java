package taewan.Smart.unit.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import taewan.Smart.domain.member.dto.MemberCertificateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.member.service.MemberCertificationServiceImpl;
import taewan.Smart.fixture.MemberTestFixture;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static taewan.Smart.global.util.PropertyUtils.getClientAddress;

@ExtendWith(MockitoExtension.class)
public class MemberCertificationServiceImplTest {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberCertificationServiceImpl memberCertificationService;

    @Test
    @DisplayName("이메일을 이용한 인증 메일 내용 테스트")
    void findEmail() {
        //given
        String email = "test@test.com";

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //when
        MemberCertificateDto result = memberCertificationService.findEmail(email);

        //then
        assertEquals(result.getMessage(), "[Smart] 회원가입 이메일 인증 안내 메일입니다.");
        assertEquals(result.getText(), getClientAddress() + "signup");
        verify(memberRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("이미 등록된 이메일을 이용한 인증 메일 진행 시 DuplicateKeyException가 발생")
    void findEmail_duplicate_email() {
        //given
        Member member = MemberTestFixture.createMember();
        String email = "test@test.com";

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));

        //when //then
        assertThrows(DuplicateKeyException.class,
                () -> memberCertificationService.findEmail(email));
        verify(memberRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("가입한 이메일을 이용해 회원 닉네임 찾기 테스트")
    void findMember_email() {
        //given
        String email = "test@test.com";
        Member member = MemberTestFixture.createMember();

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));

        //when
        MemberCertificateDto result = memberCertificationService.findMember(email);

        //then
        assertEquals(result.getMessage(), "[Smart] 회원 아이디 찾기 안내 메일입니다.");
        assertEquals(result.getText(), "회원님의 아이디는 \"" + member.getNickName() + "\"입니다.");
        verify(memberRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("가입되지 않은 이메일을 이용해 회원 닉네임을 찾는 경우 NoSuchElementException가 발생")
    void findMember_invalid_email() {
        //given
        String email = "test@test.com";

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(email));
        verify(memberRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("이메일과 닉네임을 이용한 패스워드 찾기 테스트")
    void findMember_valid_email_valid_nickName() {
        //given
        String email = "test@test.com";
        String nickName = "test";
        Member member = MemberTestFixture.createMember();

        when(memberRepository.findByNickNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.of(member));

        //when
        MemberCertificateDto result = memberCertificationService.findMember(email, nickName);

        //then
        assertEquals(result.getMessage(), "[Smart] 회원 비밀번호 찾기 안내 메일입니다.");
        assertEquals(result.getText(), "회원님의 임시 비밀번호는 \"" + member.getPassword()
                + "\"입니다. 개인정보 보호를 위해 로그인 후 변경 부탁드립니다.");
        verify(memberRepository, times(1))
                .findByNickNameAndEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("등록되지 않은 이메일과 등록된 닉네임을 이용해 패스워드를 찾는 경우 NoSuchElementException가 발생")
    void findMember_invalid_email_valid_nickName() {
        //given
        String email = "invlaid@test.com";
        String nickName = "test";

        when(memberRepository.findByNickNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(email, nickName));
        verify(memberRepository, times(1))
                .findByNickNameAndEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("등록된 이메일과 등록되지 않은 닉네임을 이용해 패스워드를 찾는 경우 NoSuchElementException가 발생")
    void findMember_valid_email_invalid_nickName() {
        //given
        String email = "test@test.com";
        String nickName = "invalid";

        when(memberRepository.findByNickNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(email, nickName));
        verify(memberRepository, times(1))
                .findByNickNameAndEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("이메일과 닉네임이 모두 등록되지 않은 상태에서 패스워드를 찾는 경우 NoSuchElementException가 발생")
    void findMember_invalid_email_invalid_nickName() {
        //given
        String email = "invalid@test.com";
        String nickName = "invalid";

        when(memberRepository.findByNickNameAndEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberCertificationService.findMember(email, nickName));
        verify(memberRepository, times(1))
                .findByNickNameAndEmail(anyString(), anyString());
    }
}
