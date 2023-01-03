package taewan.Smart.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import taewan.Smart.member.dto.MemberSaveDto;
import taewan.Smart.member.dto.MemberUpdateDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;

    public Member(MemberSaveDto dto) {
        this.memberId = dto.getMemberId();
        this.nickName = dto.getNickName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNumber = dto.getPhoneNumber();
        this.birthday = dto.getBirthday();
    }

    public void updateMember(MemberUpdateDto dto) {
        this.memberId = dto.getMemberId();
        this.nickName = dto.getNickName();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNumber = dto.getPhoneNumber();
        this.birthday = dto.getBirthday();
    }
}
