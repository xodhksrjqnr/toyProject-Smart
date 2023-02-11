package taewan.Smart.domain.order.service;

import taewan.Smart.domain.order.dto.OrderItemCancelDto;

public interface OrderItemService {

    void cancel(OrderItemCancelDto orderItemCancelDto);
    void refund(OrderItemCancelDto orderItemCancelDto);
}
