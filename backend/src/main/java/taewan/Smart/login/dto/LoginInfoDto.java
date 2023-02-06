package taewan.Smart.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginInfoDto {

    private String memberId;
    private String loginToken;
    private String refreshToken;
}
