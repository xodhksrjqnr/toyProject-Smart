package taewan.Smart.domain.order.entity;

import lombok.*;
import taewan.Smart.domain.order.dto.OrderItemInfoDto;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.status.DeliveryStatus;
import taewan.Smart.domain.product.entity.Product;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint unsigned")
    private Long orderItemId;
    @Column(columnDefinition = "int unsigned")
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "char(6)")
    private DeliveryStatus deliveryStatus;
    @Column(columnDefinition = "varchar(3)")
    private String size;

    private OrderItem(OrderItemSaveDto dto, Product product) {
        this.quantity = dto.getQuantity();
        this.size = dto.getSize();
        this.product = product;
        this.deliveryStatus = DeliveryStatus.WAIT;
    }

    public static OrderItem createOrderItem(OrderItemSaveDto dto, Product product) {
        return new OrderItem(dto, product);
    }

    void setOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        this.deliveryStatus = DeliveryStatus.CANCEL;
    }

    public void refund() {
        this.deliveryStatus = DeliveryStatus.REFUND;
    }

    public String getDeliveryStatus() {
        return this.deliveryStatus.name();
    }

    public OrderItemInfoDto toInfoDto() {
        return new OrderItemInfoDto(this);
    }
}
