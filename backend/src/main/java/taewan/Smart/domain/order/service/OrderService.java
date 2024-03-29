package taewan.Smart.domain.order.service;

import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;

import java.util.List;

public interface OrderService {

    List<OrderInfoDto> findAll(Long memberId);
    void save(Long memberId, OrderSaveDto orderSaveDto);
}
