package taewan.Smart.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthInfoDto {

    private String nickName;
    private String loginToken;
    private String refreshToken;
}
