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
import taewan.Smart.domain.member.controller.AuthController;
import taewan.Smart.domain.member.dto.MemberCertificateDto;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.service.MemberCertificationService;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.fixture.ExceptionTestFixture;
import taewan.Smart.fixture.MemberTestFixture;
import taewan.Smart.global.error.GlobalExceptionHandler;
import taewan.Smart.global.util.JwtUtils;
import taewan.Smart.infra.Mail;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static taewan.Smart.global.util.JwtUtils.createJwt;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    private MockedStatic<JwtUtils> mockedJwtUtils;
    private MockMvc mockMvc;
    @Mock
    private MemberService memberService;
    @Mock
    private MemberCertificationService memberCertificationService;
    @Mock
    private Mail mail;

    @BeforeAll
    static void beforeAllSetup() {
        MemberTestFixture.setRequestDefault();
    }

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(memberCertificationService, memberService, mail))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
        this.mockedJwtUtils = mockStatic(JwtUtils.class);
    }

    @AfterEach
    void destroy() {
        this.mockedJwtUtils.close();
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/login")
                .param("nickName", "test")
                .param("password", "test1234!");
        MemberInfoDto dto = MemberInfoDto.builder()
                .memberId(1L)
                .email("test@test.com")
                .nickName("test")
                .birthday(LocalDate.now())
                .phoneNumber("010-1111-1111")
                .build();

        when(memberService.findOne(anyString(), anyString())).thenReturn(dto);
        when(createJwt(any())).thenReturn("tokenValue");

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(memberService, only()).findOne(anyString(), anyString());
        verify(memberService, times(1)).findOne(anyString(), anyString());
        mockedJwtUtils.verify(
                () -> createJwt(any()), times(2)
        );
    }

    @Test
    @DisplayName("등록되지 않은 닉네임과 등록된 패스워드를 이용해 로그인하는 경우 isNotFound를 반환")
    void login_invalid_nickName_valid_password() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/login")
                .param("nickName", "invalid")
                .param("password", "test1234!");

        when(memberService.findOne(anyString(), anyString())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberService, only()).findOne(anyString(), anyString());
        verify(memberService, times(1)).findOne(anyString(), anyString());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }

    @Test
    @DisplayName("등록된 닉네임과 등록되지 않은 패스워드를 이용해 로그인하는 경우 isNotFound를 반환")
    void login_valid_nickName_invalid_password() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/login")
                .param("nickName", "test")
                .param("password", "invalid");

        when(memberService.findOne(anyString(), anyString())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberService, only()).findOne(anyString(), anyString());
        verify(memberService, times(1)).findOne(anyString(), anyString());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }

    @Test
    @DisplayName("닉네임과 패스워드 모두 등록되지 않았을 때 로그인하는 경우 isNotFound를 반환")
    void login_invalid_nickName_invalid_password() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/login")
                .param("nickName", "invalid")
                .param("password", "invalid");

        when(memberService.findOne(anyString(), anyString())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberService, only()).findOne(anyString(), anyString());
        verify(memberService, times(1)).findOne(anyString(), anyString());
        mockedJwtUtils.verify(
                () -> createJwt(any()), never()
        );
    }

    @Test
    @DisplayName("이메일 유효성 인증 테스트")
    void certificateEmail() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/email")
                .param("email", "test@test.com");
        MemberCertificateDto dto = new MemberCertificateDto(
                "test@test.com", "message", "text"
        );

        when(memberCertificationService.findEmail(anyString())).thenReturn(dto);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(memberCertificationService, only()).findEmail(anyString());
        verify(memberCertificationService, times(1)).findEmail(anyString());
        verify(mail, only()).sendMail(any());
        verify(mail, times(1)).sendMail(any());
    }

    @Test
    @DisplayName("이미 존재하는 이메일을 이용해 유효성 인증 시 isBadRequest를 반환")
    void certificateEmail_invalid_email() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/email")
                .param("email", "test@test.com");

        when(memberCertificationService.findEmail(anyString())).thenThrow(DuplicateKeyException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest())
                .andExpect(ExceptionTestFixture.isDuplicateKeyException());
        verify(memberCertificationService, only()).findEmail(anyString());
        verify(memberCertificationService, times(1)).findEmail(anyString());
        verify(mail, never()).sendMail(any());
    }

    @Test
    @DisplayName("이메일을 이용한 아이디 찾기 테스트")
    void certificateMemberId() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/nickname")
                .param("email", "test@test.com");
        MemberCertificateDto dto = new MemberCertificateDto(
                "test@test.com", "message", "text"
        );

        when(memberCertificationService.findMember(anyString())).thenReturn(dto);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(memberCertificationService, only()).findMember(anyString());
        verify(memberCertificationService, times(1)).findMember(anyString());
        verify(mail, only()).sendMail(any());
        verify(mail, times(1)).sendMail(any());
    }

    @Test
    @DisplayName("등록되지 않은 이메일을 이용해 아이디 찾기 시 isNotFound를 반환")
    void certificateMemberId_invalid_email() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/nickname")
                .param("email", "invalidEmail");

        when(memberCertificationService.findMember(anyString())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberCertificationService, only()).findMember(anyString());
        verify(memberCertificationService, times(1)).findMember(anyString());
        verify(mail, never()).sendMail(any());
    }

    @Test
    @DisplayName("이메일과 닉네임을 이용한 패스워드 찾기 테스트")
    void certificatePassword() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/password")
                .param("email", "test@test.com")
                .param("nickName", "test");
        MemberCertificateDto dto = new MemberCertificateDto(
                "test@test.com", "message", "text"
        );

        when(memberCertificationService.findMember(anyString(), anyString())).thenReturn(dto);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isOk());
        verify(memberCertificationService, only()).findMember(anyString(), anyString());
        verify(memberCertificationService, times(1)).findMember(anyString(), anyString());
        verify(mail, only()).sendMail(any());
        verify(mail, times(1)).sendMail(any());
    }

    @Test
    @DisplayName("등록되지 않은 이메일과 등록된 닉네임을 이용해 패스워드를 찾는 경우 isNotFound를 반환")
    void certificatePassword_invalid_email_valid_nickName() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/password")
                .param("email", "invalid")
                .param("nickName", "test");
        MemberCertificateDto dto = new MemberCertificateDto(
                "test@test.com", "message", "text"
        );

        when(memberCertificationService.findMember(anyString(), anyString())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberCertificationService, only()).findMember(anyString(), anyString());
        verify(memberCertificationService, times(1)).findMember(anyString(), anyString());
        verify(mail, never()).sendMail(any());
    }

    @Test
    @DisplayName("등록된 이메일과 등록되지 않은 닉네임을 이용해 패스워드를 찾는 경우 isNotFound를 반환")
    void certificatePassword_valid_email_invalid_nickName() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/password")
                .param("email", "test@test.com")
                .param("nickName", "invalid");
        MemberCertificateDto dto = new MemberCertificateDto(
                "test@test.com", "message", "text"
        );

        when(memberCertificationService.findMember(anyString(), anyString())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberCertificationService, only()).findMember(anyString(), anyString());
        verify(memberCertificationService, times(1)).findMember(anyString(), anyString());
        verify(mail, never()).sendMail(any());
    }

    @Test
    @DisplayName("이메일과 닉네임 모두 등록되지 않았을 때 패스워드를 찾는 경우 isNotFound를 반환")
    void certificatePassword_invalid_all() throws Exception {
        //given
        MockHttpServletRequestBuilder request = post("/members/certificate/password")
                .param("email", "invalid")
                .param("nickName", "invalid");
        MemberCertificateDto dto = new MemberCertificateDto(
                "test@test.com", "message", "text"
        );

        when(memberCertificationService.findMember(anyString(), anyString())).thenThrow(NoSuchElementException.class);

        //when
        ResultActions response = mockMvc.perform(request);

        //then
        response.andExpect(status().isNotFound())
                .andExpect(ExceptionTestFixture.isNoSuchElementException());
        verify(memberCertificationService, only()).findMember(anyString(), anyString());
        verify(memberCertificationService, times(1)).findMember(anyString(), anyString());
        verify(mail, never()).sendMail(any());
    }
}
