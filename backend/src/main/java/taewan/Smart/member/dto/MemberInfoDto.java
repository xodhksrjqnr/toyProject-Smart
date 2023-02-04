package taewan.Smart.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import taewan.Smart.member.entity.Member;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberInfoDto {

    private String memberId;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;

    public MemberInfoDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.birthday = member.getBirthday();
    }
}
