package taewan.Smart.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoDto {

    private Long memberId;
    private String nickName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;

    public Map<String, Object> toClaimsMap() {
        return Map.of(
                "memberId", memberId,
                "email", email,
                "type", "login"
        );
    }

    public Map<String, Object> toClaimMap() {
        return Map.of(
                "memberId", memberId,
                "type", "refresh"
        );
    }
}
