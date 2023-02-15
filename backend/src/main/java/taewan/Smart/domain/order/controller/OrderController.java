package taewan.Smart.domain.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderService;
import taewan.Smart.global.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Autowired
    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderInfoDto> search(HttpServletRequest request) {
        Long memberId = (Long)JwtUtil.parseJwt(request).get("memberId");

        return orderService.findAll(memberId);
    }

    @PostMapping
    public void upload(HttpServletRequest request, @RequestBody OrderSaveDto dto) {
        Long memberId = (Long)JwtUtil.parseJwt(request).get("memberId");

        orderService.save(memberId, dto);
    }

    @PostMapping("/cancel")
    public void cancel(OrderItemCancelDto dto) {
        orderItemService.cancel(dto);
    }

    @PostMapping("/refund")
    public void refund(OrderItemCancelDto dto) {
        orderItemService.refund(dto);
    }
}
