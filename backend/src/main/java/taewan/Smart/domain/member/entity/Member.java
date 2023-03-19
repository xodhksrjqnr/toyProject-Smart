package taewan.Smart.domain.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import taewan.Smart.domain.member.dto.MemberInfoDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long memberId;
    private String nickName;
    @Column(columnDefinition = "varchar(320)")
    private String email;
    private String password;
    @Column(columnDefinition = "char(13)")
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
        this.phoneNumber = dto.getPhoneNumber();
        this.birthday = dto.getBirthday();
        updateMemberPassword(dto.getPassword());
    }

    public void updateMemberPassword(String password) {
        if (password.isEmpty()) {
            return;
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
