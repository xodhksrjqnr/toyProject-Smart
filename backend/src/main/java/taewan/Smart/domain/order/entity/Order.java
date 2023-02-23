package taewan.Smart.domain.order.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemInfoDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id @GeneratedValue
    private Long orderId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @CreatedDate
    private LocalDateTime orderDateTime;

    private Order(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            this.orderItems.add(orderItem);
            orderItem.setOrder(this);
        }
    }

    public static Order createOrder(List<OrderItem> orderItems) {
        return new Order(orderItems);
    }

    public OrderInfoDto toInfoDto() {
        List<OrderItemInfoDto> orderItemInfoDtoList = new ArrayList<>();

        for (OrderItem orderItem : orderItems)
            orderItemInfoDtoList.add(orderItem.toInfoDto());
        return new OrderInfoDto(this.orderId, orderItemInfoDtoList);
    }
}
