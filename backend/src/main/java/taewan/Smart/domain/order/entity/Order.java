package taewan.Smart.domain.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemInfoDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList;
    @CreatedDate
    private LocalDateTime orderDateTime;

    public Order(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public void cancel(String status) {
        for (OrderItem orderItem : orderItemList) {
            orderItem.cancel(status);
        }
    }

    public OrderInfoDto toInfoDto(String root, String address) {
        List<OrderItemInfoDto> orderItemInfoDtoList = new ArrayList<>();

        for (OrderItem orderItem : orderItemList)
            orderItemInfoDtoList.add(orderItem.toInfoDto(root, address));
        return new OrderInfoDto(this.id, orderItemInfoDtoList);
    }
}
