package taewan.Smart.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import taewan.Smart.domain.member.entity.Member;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberInfoDto {

    private Long id;
    private String memberId;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.birthday = member.getBirthday();
    }
}
