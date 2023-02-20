package taewan.Smart.domain.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderService;

import java.util.List;

import static taewan.Smart.global.utils.JwtUtil.parseJwt;

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
    public List<OrderInfoDto> search(@RequestHeader("Authorization") String token) {
        Long memberId = Long.valueOf((Integer)parseJwt(token).get("memberId"));

        return orderService.findAll(memberId);
    }

    @PostMapping
    public void upload(@RequestHeader("Authorization") String token, @RequestBody OrderSaveDto dto) {
        Long memberId = Long.valueOf((Integer)parseJwt(token).get("memberId"));

        orderService.save(memberId, dto);
    }

    @PostMapping("/cancel")
    public void cancel(@ModelAttribute OrderItemCancelDto dto) {
        orderItemService.cancel(dto);
    }

    @PostMapping("/refund")
    public void refund(@ModelAttribute OrderItemCancelDto dto) {
        orderItemService.refund(dto);
    }
}
