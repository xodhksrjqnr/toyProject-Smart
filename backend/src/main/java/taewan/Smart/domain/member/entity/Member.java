package taewan.Smart.domain.member.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;

    public Member(MemberSaveDto dto) {
        this.memberId = dto.getMemberId();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNumber = dto.getPhoneNumber();
        this.birthday = dto.getBirthday();
    }

    public void updateMember(MemberUpdateDto dto) {
        this.memberId = dto.getMemberId();
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.phoneNumber = dto.getPhoneNumber();
        this.birthday = dto.getBirthday();
    }

    public void updateMemberPassword(String password) {
        this.password = password;
    }
}
