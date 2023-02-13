package taewan.Smart.domain.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderService;
import taewan.Smart.global.util.JwtUtils;

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
        return orderService.findAll(JwtUtils.getMemberId(request));
    }

    @PostMapping
    public void upload(HttpServletRequest request, @RequestBody OrderSaveDto orderSaveDto) {
        orderService.save(JwtUtils.getMemberId(request), orderSaveDto);
    }

    @PostMapping("/cancel")
    public void cancel(OrderItemCancelDto orderCancelDto) {
        orderItemService.cancel(orderCancelDto);
    }

    @PostMapping("/refund")
    public void refund(OrderItemCancelDto orderCancelDto) {
        orderItemService.refund(orderCancelDto);
    }
}
