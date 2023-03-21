package taewan.Smart.fixture;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class MemberTestFixture {

    private static final List<MemberSaveDto> memberSaveDtoList = createMemberSaveDtos();

    public static List<MemberSaveDto> getMemberSaveDtoList() {
        return memberSaveDtoList;
    }

    public static String createPhoneNumber() {
        String num = Integer.toString((int)(Math.random() * 100000000));
        return "010-" + num.substring(0, 4) + "-" + num.substring(4);
    }

    public static MemberSaveDto createMemberSaveDto() {
        return createMemberSaveDto(1);
    }

    public static MemberSaveDto createMemberSaveDto(int index) {
        String nickName = "member" + index;

        return MemberSaveDto.builder()
                .nickName(nickName)
                .email(nickName + "@test.com")
                .password(nickName + "1234!")
                .birthday(LocalDate.now().minusDays(index))
                .phoneNumber(createPhoneNumber())
                .build();
    }

    public static List<MemberSaveDto> createMemberSaveDtos() {
        return createMemberSaveDtos(10);
    }

    public static List<MemberSaveDto> createMemberSaveDtos(int size) {
        List<MemberSaveDto> dtos = new ArrayList<>();

        for (int i = 0; i < size; i++)
            dtos.add(createMemberSaveDto(i + 1));
        return dtos;
    }

    public static Member createMember() {
        return createMember(1);
    }

    public static Member createMember(int index) {
        String nickName = "member" + index;
        String number = Integer.toString((int)(Math.random() * 100000000));

        return Member.builder()
                .nickName(nickName)
                .email(nickName + "@naver.com")
                .password(nickName + "1234")
                .birthday(LocalDate.now().minusDays(index))
                .phoneNumber("010-" + number.substring(0, 4) + "-" + number.substring(4))
                .build();
    }

    public static List<Member> createMembers() {
        return createMembers(10);
    }

    public static List<Member> createMembers(int size) {
        List<Member> members = new ArrayList<>();

        for (int i = 0; i < size; i++)
            members.add(createMember(i + 1));
        return members;
    }

    public static String toString(MemberInfoDto memberInfoDto) {
        String str = memberInfoDto.getNickName() + ","
                + memberInfoDto.getEmail() + ","
                + memberInfoDto.getBirthday() + ","
                + memberInfoDto.getPhoneNumber();

        return str;
    }

    public static String toString(Member member) {
        String str = member.getNickName() + ","
                + member.getEmail() + ","
                + member.getBirthday() + ","
                + member.getPhoneNumber();

        return str;
    }

    //MemberController
    private static Map<String, String> requestDefault = new HashMap<>();

    public static void setRequestDefault() {
        requestDefault.put("memberId", "1");
        requestDefault.put("nickName", "test");
        requestDefault.put("email", "test@test.com");
        requestDefault.put("password", "test1234!");
        requestDefault.put("phoneNumber", "010-1111-1111");
        requestDefault.put("birthday", LocalDate.now().toString());
    }

    private static MockHttpServletRequestBuilder setParam(MockHttpServletRequestBuilder sr) {
        return sr.param("nickName", requestDefault.get("nickName"))
                .param("email", requestDefault.get("email"))
                .param("password", requestDefault.get("password"))
                .param("phoneNumber", requestDefault.get("phoneNumber"))
                .param("birthday", requestDefault.get("birthday"));
    }

    public static MockHttpServletRequestBuilder createJoinRequest() {
        return setParam(post("/members/create"));
    }

    public static MockHttpServletRequestBuilder createJoinRequest(String target, String value) {
        String savePreValue = requestDefault.get(target);
        requestDefault.put(target, value);
        MockHttpServletRequestBuilder created = createJoinRequest();
        requestDefault.put(target, savePreValue);
        return created;
    }

    public static MockHttpServletRequestBuilder createModifyRequest() {
        return setParam(post("/members/update"))
                .param("memberId", requestDefault.get("memberId"));
    }

    public static MockHttpServletRequestBuilder createModifyRequest(String target, String value) {
        String savePreValue = requestDefault.get(target);
        requestDefault.put(target, value);
        MockHttpServletRequestBuilder created = createModifyRequest();
        requestDefault.put(target, savePreValue);
        return created;
    }
}
