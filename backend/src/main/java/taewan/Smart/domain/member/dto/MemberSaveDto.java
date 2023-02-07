package taewan.Smart.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class MemberSaveDto {

    @NotEmpty(message = "[DetailErrorMessage:아이디를 입력해야합니다.]")
    private String memberId;
    @Email(message = "[DetailErrorMessage:이메일 형식이 잘못되었습니다.]")
    private String email;
    @NotEmpty(message = "[DetailErrorMessage:패스워드를 입력해야합니다.]")
    private String password;
    @Nullable
    private String phoneNumber;
    private LocalDate birthday;
}
