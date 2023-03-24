package taewan.Smart.domain.member.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateDto {

    private Long memberId;
    @NotEmpty(message = "아이디를 입력해야합니다.")
    private String nickName;
    @NotEmpty(message = "이메일을 입력해야합니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    private String password;
    @Pattern(regexp = "^(010-\\d{4}-\\d{4})|()$", message = "핸드폰 번호 형식이 잘못되었습니다.")
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
