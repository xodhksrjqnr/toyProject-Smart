package taewan.Smart.unit.member;

import taewan.Smart.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberFixture {

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
                .phoneNumber("010" + number.substring(0, 4) + number.substring(4))
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
}
