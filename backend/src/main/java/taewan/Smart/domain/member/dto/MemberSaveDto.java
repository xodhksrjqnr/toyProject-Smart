package taewan.Smart.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class MemberSaveDto {

    @NotEmpty(message = "아이디를 입력해야합니다.")
    private String memberId;
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @NotEmpty(message = "패스워드를 입력해야합니다.")
    private String password;
    @Nullable
    @Pattern(regexp = "^010-[0-9]{4}-[0-9]{4}$", message = "핸드폰 번호 형식이 잘못되었습니다.")
    private String phoneNumber;
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
