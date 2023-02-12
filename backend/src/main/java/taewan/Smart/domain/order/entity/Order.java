package taewan.Smart.domain.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();
    @CreatedDate
    private LocalDateTime orderDateTime;

    public static Order createOrder(Member member, OrderItem... orderItems) {
        Order order = new Order();

        order.setMember(member);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    public void cancel(Long orderItemId, String reason) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getOrderItemId().equals(orderItemId)) {
                orderItem.cancel(reason);
                break;
            }
        }
    }

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public OrderInfoDto toInfoDto(String root, String address) {
        List<OrderItemInfoDto> orderItemInfoDtoList = new ArrayList<>();

        for (OrderItem orderItem : orderItems)
            orderItemInfoDtoList.add(orderItem.toInfoDto(root, address));
        return new OrderInfoDto(this.orderId, orderItemInfoDtoList);
    }
}
