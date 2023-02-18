package taewan.Smart.fixture;

import taewan.Smart.domain.member.dto.MemberSaveDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberTestFixture {

    private static final List<MemberSaveDto> memberSaveDtoList = createSaveDtos();

    public static List<MemberSaveDto> getMemberSaveDtoList() {
        return memberSaveDtoList;
    }

    public static String createPhoneNumber() {
        String num = Integer.toString((int)(Math.random() * 100000000));
        return "010-" + num.substring(0, 4) + num.substring(4);
    }

    public static MemberSaveDto createSaveDto() {
        return createSaveDto(1);
    }

    public static MemberSaveDto createSaveDto(int index) {
        String nickName = "member" + index;

        return MemberSaveDto.builder()
                .nickName(nickName)
                .email(nickName + "@test.com")
                .password(nickName + "1234!")
                .birthday(LocalDate.now().minusDays(index))
                .phoneNumber(createPhoneNumber())
                .build();
    }

    public static List<MemberSaveDto> createSaveDtos() {
        return createSaveDtos(10);
    }

    public static List<MemberSaveDto> createSaveDtos(int size) {
        List<MemberSaveDto> dtos = new ArrayList<>();

        for (int i = 0; i < size; i++)
            dtos.add(createSaveDto(i + 1));
        return dtos;
    }
}
