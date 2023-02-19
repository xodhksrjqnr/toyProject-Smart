package taewan.Smart.domain.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import taewan.Smart.domain.order.dto.OrderItemInfoDto;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.product.entity.Product;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    private String deliveryStatus;
    private String size;

    public static OrderItem createOrderItem(OrderItemSaveDto orderItemSaveDto, Product product) {
        OrderItem orderItem = new OrderItem();

        orderItem.setProduct(product);
        orderItem.setSize(orderItemSaveDto.getSize());
        orderItem.setQuantity(orderItemSaveDto.getQuantity());
        orderItem.setDeliveryStatus("대기중");
        return orderItem;
    }

    public void cancel(String stats) {
        this.deliveryStatus = stats;
    }

    public OrderItemInfoDto toInfoDto() {
        return new OrderItemInfoDto(this);
    }
}
