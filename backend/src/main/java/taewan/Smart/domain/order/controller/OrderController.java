package taewan.Smart.domain.order.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taewan.Smart.domain.order.dto.OrderCancelDto;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static taewan.Smart.global.util.JwtUtils.parseJwt;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderInfoDto> search(HttpServletRequest request) {
        Claims loginToken = parseJwt(request);
        Long id = Long.parseLong((String)loginToken.get("id"));

        return orderService.findAll(id);
    }

    @PostMapping
    public void upload(HttpServletRequest request, OrderSaveDto orderSaveDto) {
        Claims loginToken = parseJwt(request);
        Long id = Long.parseLong((String)loginToken.get("id"));

        orderService.save(id, orderSaveDto);
    }

    @PostMapping("/cancel")
    public void cancel(OrderCancelDto orderCancelDto) {
        orderService.cancel(orderCancelDto);
    }

    @PostMapping("/refund")
    public void refund(OrderCancelDto orderCancelDto) {
        orderService.refund(orderCancelDto);
    }
}
