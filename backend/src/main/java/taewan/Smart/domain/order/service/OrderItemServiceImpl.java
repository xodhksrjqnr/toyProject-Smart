package taewan.Smart.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.repository.OrderItemRepository;

import static taewan.Smart.global.error.ExceptionStatus.ORDER_ITEM_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public void cancel(OrderItemCancelDto dto) {
        orderItemRepository
                .findById(dto.getOrderItemId())
                .orElseThrow(ORDER_ITEM_NOT_FOUND::exception)
                .cancel();
    }

    @Transactional
    @Override
    public void refund(OrderItemCancelDto dto) {
        orderItemRepository
                .findById(dto.getOrderItemId())
                .orElseThrow(ORDER_ITEM_NOT_FOUND::exception)
                .refund();
    }
}
