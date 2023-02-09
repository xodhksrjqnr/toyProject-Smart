package taewan.Smart.domain.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import taewan.Smart.domain.order.dto.OrderItemInfoDto;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.product.entity.Product;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private String deliveryStatus;
    private String size;

    public OrderItem(OrderItemSaveDto orderItemSaveDto, Product product) {
        this.quantity = orderItemSaveDto.getQuantity();
        this.product = product;
        this.deliveryStatus = orderItemSaveDto.getDeliveryStatus();
        this.size = orderItemSaveDto.getSize();
    }

    public void cancel(String stats) {
        this.deliveryStatus = stats;
    }

    public OrderItemInfoDto toInfoDto(String root, String address) {
        return new OrderItemInfoDto(this, root, address);
    }
}
