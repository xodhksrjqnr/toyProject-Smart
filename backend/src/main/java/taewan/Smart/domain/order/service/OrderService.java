package taewan.Smart.domain.order.service;

import taewan.Smart.domain.order.dto.OrderCancelDto;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;

import java.util.List;

public interface OrderService {

    List<OrderInfoDto> findAll(Long memberId);
    Long save(Long memberId, OrderSaveDto orderSaveDto);
    void cancel(OrderCancelDto orderCancelDto);
    void refund(OrderCancelDto orderCancelDto);
}
