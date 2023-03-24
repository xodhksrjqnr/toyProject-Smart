package taewan.Smart.unit.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.member.service.MemberServiceImpl;
import taewan.Smart.fixture.MemberTestFixture;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock private MemberRepository memberRepository;
    @InjectMocks private MemberServiceImpl memberService;

    @Test
    @DisplayName("회원 저장 테스트")
    void save() {
        //given
        MemberSaveDto dto = MemberTestFixture.createMemberSaveDto();
        Member member = MemberTestFixture.createMember();

        when(memberRepository.findByNickName(anyString())).thenReturn(Optional.empty());
        when(memberRepository.save(any())).thenReturn(member);

        //when
        memberService.save(dto);

        //then
        verify(memberRepository, times(1)).save(any());
        verify(memberRepository, times(1)).findByNickName(anyString());
    }

    @Test
    @DisplayName("회원 가입 시 회원 닉네임이 중복된 경우 DuplicateKeyException가 발생")
    void save_duplicate_nickName() {
        //given
        MemberSaveDto dto = MemberTestFixture.createMemberSaveDto();
        Member member = MemberTestFixture.createMember();

        when(memberRepository.findByNickName(anyString())).thenReturn(Optional.of(member));

        //when //then
        assertThrows(DuplicateKeyException.class,
                () -> memberService.save(dto));
        verify(memberRepository, times(1)).findByNickName(anyString());
    }

    @Test
    @DisplayName("회원 기본키를 이용한 회원 조회 테스트")
    void findOne_memberId() {
        //given
        Member member = MemberTestFixture.createMember();
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));

        //when
        MemberInfoDto found = memberService.findOne(1L);

        //then
        assertEquals(
                MemberTestFixture.toString(found),
                MemberTestFixture.toString(member)
        );
        verify(memberRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("존재하지 않는 회원 기본키를 이용해 회원 조회를 하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_memberId() {
        //given
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberService.findOne(1L));
        verify(memberRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("이메일을 이용한 회원 조회 테스트")
    void findOne_email() {
        //given
        Member member = MemberTestFixture.createMember();
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));

        //when
        MemberInfoDto found = memberService.findOne(member.getEmail());

        //then
        assertEquals(
                MemberTestFixture.toString(found),
                MemberTestFixture.toString(member)
        );
        verify(memberRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("등록되지 않은 이메일을 이용해 회원 조회를 하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_email() {
        //given
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberService.findOne("invalidEmail"));
        verify(memberRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("닉네임과 패스워드를 이용한 회원 조회 테스트")
    void findOne_valid_nickName_valid_password() {
        //given
        Member member = MemberTestFixture.createMember();
        when(memberRepository.findByNickNameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.of(member));

        //when
        MemberInfoDto found = memberService.findOne(member.getNickName(), member.getPassword());

        //then
        assertEquals(
                MemberTestFixture.toString(found),
                MemberTestFixture.toString(member)
        );
        verify(memberRepository, times(1))
                .findByNickNameAndPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("등록되지 않은 닉네임과 등록된 패스워드를 이용해 회원 조회를 하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_nickName_valid_password() {
        //given
        when(memberRepository.findByNickNameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberService.findOne("invalidNickName", "validPassword"));
        verify(memberRepository, times(1))
                .findByNickNameAndPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("등록된 닉네임과 등록되지 않은 패스워드를 이용해 회원 조회를 하는 경우 NoSuchElementException가 발생")
    void findOne_valid_nickName_invalid_password() {
        //given
        when(memberRepository.findByNickNameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberService.findOne("validNickName", "invalidPassword"));
        verify(memberRepository, times(1))
                .findByNickNameAndPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("등록되지 않은 닉네임과 등록되지 않은 패스워드를 이용해 회원 조회를 하는 경우 NoSuchElementException가 발생")
    void findOne_invalid_nickName_invalid_password() {
        //given
        when(memberRepository.findByNickNameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberService.findOne("invalidNickName", "invalidPassword"));
        verify(memberRepository, times(1))
                .findByNickNameAndPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void update() {
        //given
        Member member = MemberTestFixture.createMember();
        String preNickName = member.getNickName();
        MemberUpdateDto dto = MemberUpdateDto.builder()
                .nickName(member.getNickName() + "111")
                .email(member.getEmail())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .birthday(member.getBirthday())
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        when(memberRepository.findByNickName(anyString())).thenReturn(Optional.empty());

        //when
        MemberInfoDto updated = memberService.update(dto);

        //then
        assertNotEquals(updated.getNickName(), preNickName);
        verify(memberRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findByNickName(anyString());
    }

    @Test
    @DisplayName("존재하지 않는 회원 기본키를 이용해 수정하려는 경우 NoSuchElementException가 발생")
    void update_invalid_memberId() {
        //given
        Member member = MemberTestFixture.createMember();
        MemberUpdateDto dto = MemberUpdateDto.builder()
                .nickName(member.getNickName() + "111")
                .email(member.getEmail())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .birthday(member.getBirthday())
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberService.update(dto));
        verify(memberRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("이미 등록된 회원 닉네임으로 수정하는 경우 DuplicateKeyException가 발생")
    void update_duplicate_nickName() {
        //given
        Member member = MemberTestFixture.createMember();
        Member uploaded = MemberTestFixture.createMember(2);
        MemberUpdateDto dto = MemberUpdateDto.builder()
                .nickName(uploaded.getNickName())
                .email(member.getEmail())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .birthday(member.getBirthday())
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        when(memberRepository.findByNickName(anyString())).thenReturn(Optional.of(uploaded));

        //when //then
        assertThrows(DuplicateKeyException.class,
                () -> memberService.update(dto));
        verify(memberRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findByNickName(anyString());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void delete() {
        //given
        Long memberId = 1L;

        //when
        memberService.delete(memberId);

        //then
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 회원 기본키를 이용해 탈퇴하는 경우 NoSuchElementException가 발생")
    void delete_invalid_memberId() {
        //given
        Long memberId = 0L;

        doThrow(EmptyResultDataAccessException.class)
                .when(memberRepository)
                .deleteById(any());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> memberService.delete(memberId));
        verify(memberRepository, times(1)).deleteById(any());
    }
}