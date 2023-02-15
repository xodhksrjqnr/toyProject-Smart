package taewan.Smart.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import taewan.Smart.domain.member.entity.Member;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberInfoDto {

    private Long memberId;
    private String nickName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;

    public MemberInfoDto(Member member) {
        this.memberId = member.getMemberId();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
        this.birthday = member.getBirthday();
    }

    public MemberInfoDto(MemberUpdateDto dto) {
        this.memberId = dto.getMemberId();
        this.nickName = dto.getNickName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.birthday = dto.getBirthday();
    }

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
