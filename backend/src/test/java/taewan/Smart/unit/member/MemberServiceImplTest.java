package taewan.Smart.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.member.service.MemberServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock private MemberRepository memberRepository;
    @InjectMocks private MemberServiceImpl memberService;

    static List<MemberSaveDto> dtos = new ArrayList<>();
    static List<Member> entities = new ArrayList<>();

//    @BeforeAll
//    static void setUp() {
//        for (int i = 1; i <= 3; i++) {
//            MemberSaveDto dto = new MemberSaveDto();
//            dto.setNickName("test" + i);
//            dto.setEmail("test" + i + "@test.com");
//            dto.setPassword("test1234" + i);
//            dto.setPhoneNumber("0101234123" + i);
//            dto.setBirthday(LocalDate.now().plusDays(i));
//            dtos.add(dto);
//
//            Member member = new Member();
//            MemberUpdateDto dto2 = new MemberUpdateDto();
//            dto2.setMemberId((long)i);
//            dto2.setNickName("test" + i);
//            dto2.setEmail("test" + i + "@test.com");
//            dto2.setPassword("test1234" + i);
//            dto2.setPhoneNumber("0101234123" + i);
//            dto2.setBirthday(LocalDate.now().plusDays(i));
//            member.updateMember(dto2);
//            entities.add(member);
//        }
//    }
//
//    @Test
//    void 회원_저장() {
//        //given
//        Member member = new Member(dtos.get(0));
//        Mockito.when(memberRepository.save(member)).thenReturn(entities.get(0));
//
//        //when
//        Long savedId = memberService.save(dtos.get(0));
//
//        //then
//        Assertions.assertThat(savedId).isEqualTo(1);
//        Mockito.verify(memberRepository, Mockito.times(1)).save(member);
//    }
//
//    @Test
//    void 회원_단일조회() {
//        //given
//        Member member = entities.get(0);
//        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
//
//        //when
//        MemberInfoDto found = memberService.findOne(1L);
//
//        //then
//        Assertions.assertThat(found.toString().replace("MemberInfoDto", ""))
//                        .isEqualTo(member.toString().replace("Member", ""));
//        Mockito.verify(memberRepository, Mockito.times(1)).findById(1L);
//    }
//
//    @Test
//    void 없는_회원_단일조회() {
//        //given
//        Member member = entities.get(0);
//        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.empty());
//
//        //when //then
//        assertThrows(NoSuchElementException.class, () -> memberService.findOne(1L));
//        Mockito.verify(memberRepository, Mockito.times(1)).findById(1L);
//    }
//
//    @Test
//    void 회원_수정() {
//        //given
//        Member member = entities.get(0);
//        MemberUpdateDto dto = new MemberUpdateDto();
//        dto.setMemberId(1L);
//        dto.setNickName("test5");
//        dto.setEmail("test1@test.com");
//        dto.setPassword("test12341");
//        dto.setPhoneNumber("01012341235");
//        dto.setBirthday(LocalDate.now().plusDays(5));
//        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
//
//        //when
//        Long id = memberService.update(dto);
//
//        //then
//        Assertions.assertThat(id).isEqualTo(1L);
//        Assertions.assertThat(member.toString().replace("Member", ""))
//                        .isEqualTo(dto.toString().replace("MemberUpdateDto", ""));
//        Mockito.verify(memberRepository, Mockito.times(1)).findById(1L);
//    }
//
//    @Test
//    void 회원_탈퇴() {
//        //given
//
//        //when
//        memberService.delete(1L);
//
//        //then
//        Mockito.verify(memberRepository, Mockito.times(1)).deleteById(1L);
//    }
}