package taewan.Smart.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.entity.Member;
import taewan.Smart.member.repository.MemberRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;

    static List<MemberSaveDto> dtos = new ArrayList<>();

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
//        }
//    }
//
//    @Test
//    void 회원_저장() {
//        //given
//        MemberSaveDto dto = dtos.get(0);
//
//        //when
//        Member saved = memberRepository.save(new Member(dto));
//
//        //then
//        Assertions.assertThat(dto.getNickName()).isEqualTo(saved.getNickName());
//        Assertions.assertThat(dto.getEmail()).isEqualTo(saved.getEmail());
//        Assertions.assertThat(dto.getPassword()).isEqualTo(saved.getPassword());
//        Assertions.assertThat(dto.getPhoneNumber()).isEqualTo(saved.getPhoneNumber());
//        Assertions.assertThat(dto.getBirthday()).isEqualTo(saved.getBirthday());
//    }
//
//    @Test
//    void 회원_단일조회() {
//        //given
//        MemberSaveDto dto = dtos.get(0);
//        Member saved = memberRepository.save(new Member(dto));
//
//        //when
//        Member found = memberRepository.findById(saved.getMemberId()).orElseThrow();
//
//        //then
//        Assertions.assertThat(found.toString()).isEqualTo(saved.toString());
//    }
//
//    @Test
//    void 없는_회원_단일조회() {
//        //given
//
//        //when
//        Optional<Member> found = memberRepository.findById(1L);
//
//        //then
//        Assertions.assertThat(found).isEmpty();
//    }
//
//    @Test
//    void 회원_전체조회() {
//        //given
//        List<Member> saved = new ArrayList<>();
//
//        for (MemberSaveDto dto : dtos)
//            saved.add(memberRepository.save(new Member(dto)));
//
//        //when
//        List<Member> found = memberRepository.findAll();
//
//        //then
//        Assertions.assertThat(found.size()).isEqualTo(3);
//        for (int i = 0; i < 3; i++)
//            Assertions.assertThat(found.get(i).toString()).isEqualTo(saved.get(i).toString());
//    }
//
//
//    @Test
//    void 회원_삭제() {
//        //given
//        MemberSaveDto dto = dtos.get(0);
//        Member saved = memberRepository.save(new Member(dto));
//
//        //when
//        memberRepository.deleteById(saved.getMemberId());
//
//        //then
//        Assertions.assertThat(memberRepository.count()).isEqualTo(0);
//        Assertions.assertThat(memberRepository.findById(saved.getMemberId())).isEmpty();
//    }
}