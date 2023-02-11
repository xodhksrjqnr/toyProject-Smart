package taewan.Smart.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthInfoDto {

    private String nickName;
    private String loginToken;
    private String refreshToken;
}
