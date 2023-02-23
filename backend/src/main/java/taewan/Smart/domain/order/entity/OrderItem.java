package taewan.Smart.domain.order.entity;

import lombok.*;
import taewan.Smart.domain.order.dto.OrderItemInfoDto;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.product.entity.Product;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    private enum Status {
        WAIT, CANCEL, REFUND
    }

    @Id @GeneratedValue
    private Long orderItemId;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Enumerated(value = EnumType.STRING)
    private Status deliveryStatus;
    private String size;

    private OrderItem(OrderItemSaveDto dto, Product product) {
        this.quantity = dto.getQuantity();
        this.size = dto.getSize();
        this.product = product;
        this.deliveryStatus = Status.WAIT;
    }

    public static OrderItem createOrderItem(OrderItemSaveDto dto, Product product) {
        return new OrderItem(dto, product);
    }

    void setOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        this.deliveryStatus = Status.CANCEL;
    }

    public void refund() {
        this.deliveryStatus = Status.REFUND;
    }

    public String getDeliveryStatus() {
        return this.deliveryStatus.name();
    }

    public OrderItemInfoDto toInfoDto() {
        return new OrderItemInfoDto(this);
    }
}
