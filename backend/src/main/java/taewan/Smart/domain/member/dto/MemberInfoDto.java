package taewan.Smart.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class MemberInfoDto {

    private Long memberId;
    private String nickName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;

    public Map<String, Object> toClaimsMap() {
        return Map.of(
                "memberId", this.memberId,
                "email", this.email
        );
    }

    public Map<String, Object> toClaimMap() {
        return Map.of("memberId", this.memberId);
    }
}
