package taewan.Smart.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import taewan.Smart.domain.member.entity.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberSaveDto {

    @NotBlank(message = "아이디를 입력해야합니다.")
    private String nickName;
    @NotBlank(message = "이메일을 입력해야합니다.")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String email;
    @NotBlank(message = "패스워드를 입력해야합니다.")
    private String password;
    @Pattern(regexp = "^(010-\\d{4}-\\d{4})|()$", message = "핸드폰 번호 형식이 잘못되었습니다.")
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public Member toEntity() {
        return Member.builder()
                .nickName(nickName)
                .password(password)
                .email(email)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .build();
    }
}
