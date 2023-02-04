package taewan.Smart.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MemberUpdateDto {

    @NotEmpty
    private String memberId;
    @NotEmpty
    private String email;
    @Nullable
    private String password;
    @Nullable
    private String phoneNumber;
    @Nullable
    private LocalDate birthday;
}
