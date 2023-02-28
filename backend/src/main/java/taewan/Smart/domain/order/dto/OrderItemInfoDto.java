package taewan.Smart.domain.order.dto;

import lombok.Getter;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.global.converter.PathConverter;
import taewan.Smart.global.util.CustomFileUtils;


@Getter
public class OrderItemInfoDto {

    private Long productId;
    private Long orderItemId;
    private String thumbnail;
    private String name;
    private String size;
    private Integer quantity;
    private Integer price;
    private String deliveryStatus;

    public OrderItemInfoDto(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getProductId();
        this.orderItemId = orderItem.getOrderItemId();
        this.thumbnail = PathConverter.toImgAccessUrl(
                CustomFileUtils.findFilePaths(orderItem.getProduct().getImgPath())
        ).get(0);
        this.name = orderItem.getProduct().getName();
        this.size = orderItem.getSize();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getProduct().getPrice() * this.quantity;
        this.deliveryStatus = orderItem.getDeliveryStatus();
    }
}
