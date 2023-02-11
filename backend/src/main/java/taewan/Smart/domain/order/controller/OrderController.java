package taewan.Smart.domain.order.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static taewan.Smart.global.util.JwtUtils.parseJwt;

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
        Claims loginToken = parseJwt(request);
        Long id = Long.parseLong((String)loginToken.get("memberId"));

        return orderService.findAll(id);
    }

    @PostMapping
    public void upload(HttpServletRequest request, @RequestBody OrderSaveDto orderSaveDto) {
        Claims loginToken = parseJwt(request);
        Long id = Long.parseLong((String)loginToken.get("memberId"));

        orderService.save(id, orderSaveDto);
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
