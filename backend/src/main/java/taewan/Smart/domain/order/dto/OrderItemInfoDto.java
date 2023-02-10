package taewan.Smart.domain.order.dto;

import lombok.Getter;
import lombok.Setter;
import taewan.Smart.domain.order.entity.OrderItem;

import static taewan.Smart.global.util.FileUtils.findFile;

@Getter
@Setter
public class OrderItemInfoDto {

    private Long productId;
    private String thumbnail;
    private String name;
    private String size;
    private Integer quantity;
    private Integer price;
    private String deliveryStatus;

    public OrderItemInfoDto(OrderItem orderItem, String root, String address) {
        this.productId = orderItem.getProduct().getProductId();
        this.thumbnail = findFile(orderItem.getProduct().getImgFolderPath(), root, address);
        this.name = orderItem.getProduct().getName();
        this.size = orderItem.getSize();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getProduct().getPrice() * this.quantity;
        this.deliveryStatus = orderItem.getDeliveryStatus();
    }
}
