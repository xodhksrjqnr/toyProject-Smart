package taewan.Smart.unit.member;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import taewan.Smart.domain.member.controller.MemberController;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.fixture.ExceptionTestFixture;
import taewan.Smart.fixture.MemberTestFixture;
import taewan.Smart.global.error.GlobalExceptionHandler;
import taewan.Smart.global.util.JwtUtils;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static taewan.Smart.global.util.JwtUtils.createJwt;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    private MockedStatic<JwtUtils> mockedJwtUtils;
    private MockMvc mockMvc;
    @Mock private MemberService memberService;

    @BeforeAll
    static void beforeAllSetup() {
        MemberTestFixture.setRequestDefault();
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new MemberController(memberService))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
        this.mockedJwtUtils = mockStatic(JwtUtils.class);
    }

    @AfterEach
    void destroy() {
        this.mockedJwtUtils.close();
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void save() throws Exception {
        //given
        MockHttpServletRequestBuilder request = MemberTestFixture.createJoinRequest();

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isCreated());
        verify(memberService, only()).save(any());
        verify(memberService, times(1)).save(any());
    }

    @Test
    @DisplayName("회원 닉네임이 비어있는 경우 isBadRequest 반환")
    void save_invalid_nickName() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 =
                MemberTestFixture.createJoinRequest("nickName", " ");
        MockHttpServletRequestBuilder request2 =
                MemberTestFixture.createJoinRequest("nickName", "");

        //when
        ResultActions isBlank = mockMvc.perform(request1);
        ResultActions isNull = mockMvc.perform(request2);

        //then
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(memberService, never()).save(any());
    }

    @Test
    @DisplayName("회원 이메일이 올바르지 않은 경우 isBadRequest 반환")
    void save_invalid_email() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 =
                MemberTestFixture.createJoinRequest("email", "invalidEmailPattern");
        MockHttpServletRequestBuilder request2 =
                MemberTestFixture.createJoinRequest("email", " ");
        MockHttpServletRequestBuilder request3 =
                MemberTestFixture.createJoinRequest("email", "");

        //when
        ResultActions invalidPattern = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);
        ResultActions isNull = mockMvc.perform(request3);

        //then
        invalidPattern.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(memberService, never()).save(any());
    }

    @Test
    @DisplayName("회원 패스워드가 입려되지 않은 경우 isBadRequest 반환")
    void save_invalid_password() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 =
                MemberTestFixture.createJoinRequest("password", " ");
        MockHttpServletRequestBuilder request2 =
                MemberTestFixture.createJoinRequest("password", "");

        //when
        ResultActions isBlank = mockMvc.perform(request1);
        ResultActions isNull = mockMvc.perform(request2);

        //then
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isNull.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        verify(memberService, never()).save(any());
    }

    @Test
    @DisplayName("회원 핸드폰 번호는 필수는 아니지만 입력했을 때 올바르지 않은 경우 isBadRequest 반환")
    void save_invalid_phoneNumber() throws Exception {
        //given
        MockHttpServletRequestBuilder request1 =
                MemberTestFixture.createJoinRequest("phoneNumber", "0000000-3333");
        MockHttpServletRequestBuilder request2 =
                MemberTestFixture.createJoinRequest("phoneNumber", " ");
        MockHttpServletRequestBuilder request3 =
                MemberTestFixture.createJoinRequest("phoneNumber", "");

        //when
        ResultActions invalidPattern = mockMvc.perform(request1);
        ResultActions isBlank = mockMvc.perform(request2);
        ResultActions isNull = mockMvc.perform(request3);

        //then
        invalidPattern.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isBlank.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isBindException());
        isNull.andExpect(status().isCreated());
        verify(memberService, only()).save(any());
        verify(memberService, times(1)).save(any());
    }

    @Test
    @DisplayName("가입하려는 회원 닉네임이 중복된 경우 isBadRequest 반환")
    void save_duplicate_nickName() throws Exception {
        //given
        MockHttpServletRequestBuilder request = MemberTestFixture.createJoinRequest();

        doThrow(DuplicateKeyException.class).when(memberService).save(any());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isDuplicateKeyException());
        verify(memberService, only()).save(any());
        verify(memberService, times(1)).save(any());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void search() throws Exception {
        //given
        MockHttpServletRequestBuilder request = get("/members")
                .requestAttr("tokenMemberId", 1L);
        MemberInfoDto dto = MemberTestFixture.createMember().toInfoDto();

        when(memberService.findOne(anyLong())).thenReturn(dto);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(memberService, only()).findOne(anyLong());
        verify(memberService, times(1)).findOne(anyLong());
    }

    @Test
    @DisplayName("회원 기본키가 존재하지 않는 경우 isNotFound를 반환")
    void search_invalid_memberId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = get("/members")
                .requestAttr("tokenMemberId", 0L);

        when(memberService.findOne(anyLong())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberService, only()).findOne(anyLong());
        verify(memberService, times(1)).findOne(anyLong());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void modify() throws Exception {
        //given
        MockHttpServletRequestBuilder request = MemberTestFixture.createModifyRequest()
                .requestAttr("tokenMemberId", 1L);
        MemberInfoDto dto = MemberInfoDto.builder()
                .memberId(1L)
                .nickName("test")
                .email("test@test.com")
                .build();

        when(memberService.update(any())).thenReturn(dto);
        when(createJwt(any())).thenReturn("tokenValue");

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(memberService, only()).update(any());
        verify(memberService, times(1)).update(any());
        mockedJwtUtils.verify(
                () -> createJwt(any()), times(2)
        );
    }

    @Test
    @DisplayName("토큰에 저장된 회원 기본키와 update 대상의 회원 기본키가 다른 경우 isUnauthorized를 반환")
    void modify_diff_tokenMemberId_And_updateDtoMemberId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = MemberTestFixture.createModifyRequest()
                .requestAttr("tokenMemberId", 2L);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isUnauthorized())
                .andExpect(ExceptionTestFixture.isAuthAccessException());
        verify(memberService, never()).update(any());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }

    @Test
    @DisplayName("수정하려는 memberId가 존재하지 않는 경우 isNotFound를 반환")
    void modify_invalid_updateDtoMemberId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = MemberTestFixture.createModifyRequest()
                .requestAttr("tokenMemberId", 1L);

        when(memberService.update(any())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberService, only()).update(any());
        verify(memberService, times(1)).update(any());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }

    @Test
    @DisplayName("수정하려는 nickName이 중복된 경우 isBadRequest를 반환")
    void modify_duplicate_nickName() throws Exception {
        //given
        MockHttpServletRequestBuilder request = MemberTestFixture.createModifyRequest()
                .requestAttr("tokenMemberId", 1L);

        when(memberService.update(any())).thenThrow(DuplicateKeyException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isDuplicateKeyException());
        verify(memberService, only()).update(any());
        verify(memberService, times(1)).update(any());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void remove() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/delete")
                .requestAttr("tokenMemberId", 1L);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(memberService, only()).delete(anyLong());
        verify(memberService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("등록되지 않은 회원 기본키를 이용해 회원 탈퇴를 하는 경우 isNotFound를 반환")
    void remove_invalid_memberId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/delete")
                .requestAttr("tokenMemberId", 0L);

        doThrow(NoSuchElementException.class).when(memberService).delete(anyLong());

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberService, only()).delete(anyLong());
        verify(memberService, times(1)).delete(anyLong());
    }

    @Test
    @DisplayName("회원 기본키와 refresh 토큰을 이용한 새 토큰 발행 테스트")
    void refresh() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/refresh")
                .requestAttr("tokenMemberId", 1L)
                .requestAttr("tokenType", "refresh");
        MemberInfoDto dto = MemberInfoDto.builder()
                .memberId(1L)
                .nickName("test")
                .email("test@test.com")
                .build();

        when(memberService.findOne(anyLong())).thenReturn(dto);
        when(createJwt(any())).thenReturn("tokenValue");

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(memberService, only()).findOne(anyLong());
        verify(memberService, times(1)).findOne(anyLong());
        mockedJwtUtils.verify(
                () -> createJwt(any()), times(2)
        );
    }

    @Test
    @DisplayName("등록되지 않은 회원 기본키와 refresh 토큰을 이용해 새 토큰을 발행할 때 isNotFound를 반환")
    void refresh_invalid_memberId_refresh() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/refresh")
                .requestAttr("tokenMemberId", 0L)
                .requestAttr("tokenType", "refresh");

        when(memberService.findOne(anyLong())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberService, only()).findOne(anyLong());
        verify(memberService, times(1)).findOne(anyLong());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }

    @Test
    @DisplayName("등록된 회원 기본키와 refresh 토큰이 아닌 토큰을 이용해 새 토큰을 발행할 때 isUnauthorized를 반환")
    void refresh_valid_memberId_not_refresh() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/refresh")
                .requestAttr("tokenMemberId", 1L)
                .requestAttr("tokenType", "login");

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isUnauthorized())
                .andExpect(ExceptionTestFixture.isJwtException());
        verify(memberService, never()).findOne(anyLong());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }
}