package taewan.Smart.domain.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemInfoDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @CreatedDate
    private LocalDateTime orderDateTime;

    public void add(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void cancel(String status) {
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(status);
        }
    }

    public OrderInfoDto toInfoDto(String root, String address) {
        List<OrderItemInfoDto> orderItemInfoDtoList = new ArrayList<>();

        for (OrderItem orderItem : orderItems)
            orderItemInfoDtoList.add(orderItem.toInfoDto(root, address));
        return new OrderInfoDto(this.orderId, orderItemInfoDtoList);
    }
}
