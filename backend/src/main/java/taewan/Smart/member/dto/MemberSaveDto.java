package taewan.Smart.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class MemberSaveDto {

    @NotEmpty(message = "[DetailErrorMessage:아이디가 필요합니다.]")
    private String memberId;
    @Email(message = "[DetailErrorMessage:이메일이 필요합니다.]")
    private String email;
    @NotEmpty(message = "[DetailErrorMessage:패스워드가 필요합니다.]")
    private String password;
    @Nullable
    private String phoneNumber;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
