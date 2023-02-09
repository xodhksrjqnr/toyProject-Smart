package taewan.Smart.domain.member.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import taewan.Smart.domain.member.dto.MemberSaveDto;
import taewan.Smart.domain.member.dto.MemberUpdateDto;
import taewan.Smart.domain.order.entity.Order;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private List<Order> orders;

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

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
