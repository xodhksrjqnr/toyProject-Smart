package taewan.Smart.domain.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.entity.Member;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.order.dto.OrderCancelDto;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.entity.Order;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderItemRepository;
import taewan.Smart.domain.order.repository.OrderRepository;
import taewan.Smart.domain.product.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import static taewan.Smart.global.error.ExceptionStatus.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${server.address.basic}")
    private String address;

    @Value("${root.path}")
    private String root;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                            MemberRepository memberRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderInfoDto> findAll(Long memberId) {
        List<Order> orders = orderRepository.findAllByMemberId(memberId);
        List<OrderInfoDto> orderInfoDtoList = new ArrayList<>();

        for (Order order : orders) {
            orderInfoDtoList.add(order.toInfoDto(root, address));
        }
        return orderInfoDtoList;
    }

    @Transactional
    @Override
    public Long save(Long memberId, OrderSaveDto orderSaveDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MEMBER_NOT_FOUND::exception);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemSaveDto dto : orderSaveDto.getOrderItemSaveDtoList()) {
            orderItemList.add(
                    orderItemRepository.save(
                            new OrderItem(dto, productRepository.findById(dto.getProductId())
                    .orElseThrow(PRODUCT_NOT_FOUND::exception)))
            );
        }

        Order order = new Order(orderItemList);

        member.addOrder(order);
        return orderRepository.save(order).getId();
    }

    @Transactional
    @Override
    public void cancel(OrderCancelDto orderCancelDto) {
        orderRepository.findAllById(orderCancelDto.getId())
                .orElseThrow(ORDER_NOT_FOUND::exception)
                .cancel("취소");
    }

    @Transactional
    @Override
    public void refund(OrderCancelDto orderCancelDto) {
        orderRepository.findAllById(orderCancelDto.getId())
                .orElseThrow(ORDER_NOT_FOUND::exception)
                .cancel("환불");
    }
}
