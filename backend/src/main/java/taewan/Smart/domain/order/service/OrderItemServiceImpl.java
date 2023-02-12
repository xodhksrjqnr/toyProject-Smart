package taewan.Smart.domain.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderItemRepository;

import static taewan.Smart.global.error.ExceptionStatus.ORDER_ITEM_NOT_FOUND;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    @Override
    public void cancel(OrderItemCancelDto orderItemCancelDto) {
        OrderItem orderItem = orderItemRepository.findById(orderItemCancelDto.getOrderItemId())
                .orElseThrow(ORDER_ITEM_NOT_FOUND::exception);

        orderItem.setDeliveryStatus("취소");
    }

    @Transactional
    @Override
    public void refund(OrderItemCancelDto orderItemCancelDto) {
        OrderItem orderItem = orderItemRepository.findById(orderItemCancelDto.getOrderItemId())
                .orElseThrow(ORDER_ITEM_NOT_FOUND::exception);

        orderItem.setDeliveryStatus("환불");
    }
}
