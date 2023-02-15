package taewan.Smart.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.order.entity.Order;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthday;
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String nickName, String email, String password, String phoneNumber, LocalDate birthday) {
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
        if (!password.isEmpty())
            this.password = password;
    }
}
