package taewan.Smart.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.entity.Order;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderRepository;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static taewan.Smart.global.error.ExceptionStatus.MEMBER_NOT_FOUND;
import static taewan.Smart.global.error.ExceptionStatus.PRODUCT_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductDao productDao;

    @Override
    public List<OrderInfoDto> findAll(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(MEMBER_NOT_FOUND::exception);
        return orderRepository
                .findAllByMemberId(memberId)
                .stream().map(Order::toInfoDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long save(Long memberId, OrderSaveDto dto) {
        memberRepository.findById(memberId).orElseThrow(MEMBER_NOT_FOUND::exception);

        List<OrderItem> orderItems = new ArrayList<>();

        dto.getOrderItemSaveDtoList()
                .forEach(oi -> {
                    Product product = productDao.findById(oi.getProductId())
                            .orElseThrow(PRODUCT_NOT_FOUND::exception);
                    orderItems.add(OrderItem.createOrderItem(oi, product));
                });

        Order order = Order.createOrder(memberId, orderItems);

        return orderRepository.save(order).getOrderId();
    }
}
