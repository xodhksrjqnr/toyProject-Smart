package taewan.Smart.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberUpdateDto {

    private Long memberId;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
}
