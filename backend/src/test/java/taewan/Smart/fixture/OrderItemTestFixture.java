package taewan.Smart.fixture;

import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.entity.OrderItem;

public class OrderItemTestFixture {

    public static OrderItemSaveDto createOrderItemSaveDto(Long productId) {
        return new OrderItemSaveDto(productId, "s", 1);
    }

    public static String toString(OrderItem orderItem) {
        String str = orderItem.getOrderItemId() + ","
                + orderItem.getQuantity() + ","
                + orderItem.getSize() + ","
                + orderItem.getDeliveryStatus() + ","
                + orderItem.getProduct().getProductId();

        return str;
    }
}
