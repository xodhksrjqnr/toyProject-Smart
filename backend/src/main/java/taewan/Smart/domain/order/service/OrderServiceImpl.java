package taewan.Smart.domain.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.entity.Order;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderRepository;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import static taewan.Smart.global.error.ExceptionStatus.MEMBER_NOT_FOUND;
import static taewan.Smart.global.error.ExceptionStatus.PRODUCT_NOT_FOUND;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, MemberRepository memberRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderInfoDto> findAll(Long memberId) {
        List<Order> orders = memberRepository.findById(memberId)
                .orElseThrow(MEMBER_NOT_FOUND::exception).getOrders();

        List<OrderInfoDto> orderInfoDtoList = new ArrayList<>();

        for (Order order : orders) {
            orderInfoDtoList.add(order.toInfoDto());
        }
        return orderInfoDtoList;
    }

    @Transactional
    @Override
    public Long save(Long memberId, OrderSaveDto orderSaveDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MEMBER_NOT_FOUND::exception);
        Order order = Order.createOrder(member);

        for (OrderItemSaveDto dto : orderSaveDto.getOrderItemSaveDtoList()) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(PRODUCT_NOT_FOUND::exception);
            order.addOrderItem(OrderItem.createOrderItem(dto, product));
        }
        return orderRepository.save(order).getOrderId();
    }
}
