package taewan.Smart.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberSaveDto {

    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
}
