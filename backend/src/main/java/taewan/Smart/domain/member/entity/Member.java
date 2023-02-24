package taewan.Smart.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

import static taewan.Smart.global.error.ExceptionStatus.MEMBER_PASSWORD_INVALID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    private Long memberId;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;

    @Builder
    private Member(String nickName, String email, String password, String phoneNumber, LocalDate birthday) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    public void updateMember(MemberUpdateDto dto) {
        this.nickName = dto.getNickName();
        this.email = dto.getEmail();
        this.phoneNumber = dto.getPhoneNumber();
        this.birthday = dto.getBirthday();
        updateMemberPassword(dto.getPassword());
    }

    public void updateMemberPassword(String password) {
        if (password.isEmpty()) {
            throw MEMBER_PASSWORD_INVALID.exception();
        }
        this.password = password;
    }

    public MemberInfoDto toInfoDto() {
        return MemberInfoDto.builder()
                .memberId(memberId)
                .nickName(nickName)
                .email(email)
                .phoneNumber(phoneNumber)
                .birthday(birthday)
                .build();
    }
}
