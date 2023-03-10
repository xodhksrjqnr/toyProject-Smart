package taewan.Smart.unit.member;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import taewan.Smart.domain.member.controller.MemberController;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.service.MemberServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean
    MemberServiceImpl memberService;
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
//    void ??????_????????????() throws Exception {
//        //given
//        when(memberService.findOne(1L))
//                .thenReturn(new MemberInfoDto(entities.get(0)));
//
//        //when //then
//        mockMvc.perform(get("/member/{memberId}", 1))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("member"))
//                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
//                .andExpect(view().name("member/view"));
//    }
//
//    @Test
//    void ??????_??????_????????????() throws Exception {
//        //given
//        when(memberService.findOne(1L))
//                .thenThrow(new NoSuchElementException());
//
//        //when //then
//        mockMvc.perform(get("/member/{memberId}", 1))
//                .andExpect(status().isNotFound())
//                .andExpect(e -> assertTrue(e.getResolvedException() instanceof NoSuchElementException))
//                .andExpect(view().name("error"));
//    }
//
//    @Test
//    void ??????_????????????() throws Exception {
//        //when //then
//        mockMvc.perform(get("/member/create"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("member"))
//                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
//                .andExpect(view().name("member/write"));
//    }
//
//    @Test
//    void ??????_??????() throws Exception {
//        //given
//        when(memberService.save(dtos.get(0))).thenReturn(1L);
//
//        //when //then
//        mockMvc.perform(post("/member"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrlPattern("/member/{memberId}"));
//    }
//
//    @Test
//    void ????????????_????????????() throws Exception {
//        //given
//        when(memberService.findOne(1L))
//                .thenReturn(new MemberInfoDto(entities.get(0)));
//
//        //when //then
//        mockMvc.perform(get("/member/update/{memberId}", 1))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("member"))
//                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
//                .andExpect(view().name("member/update"));
//    }
//
//    @Test
//    void ????????????_??????() throws Exception {
//        //given
//        MemberUpdateDto dto = new MemberUpdateDto();
//        dto.setMemberId(1L);
//        dto.setNickName("test5");
//        dto.setEmail("test5@test.com");
//        dto.setPassword("test12345");
//        dto.setPhoneNumber("01012341235");
//        dto.setBirthday(LocalDate.now().plusDays(5));
//        when(memberService.update(dto)).thenReturn(1L);
//
//        //when //then
//        mockMvc.perform(post("/member/update"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrlPattern("/member/{memberId}"));
//    }
//
//    @Test
//    void ??????_??????() throws Exception {
//        //when //then
//        mockMvc.perform(post("/member/delete/{memberId}", 1))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/member"));
//    }
}