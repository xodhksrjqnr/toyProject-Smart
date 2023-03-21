package taewan.Smart.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import taewan.Smart.domain.order.dto.OrderInfoDto;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping
    public List<OrderInfoDto> search(@RequestAttribute Long tokenMemberId) {
        return orderService.findAll(tokenMemberId);
    }

    @PostMapping
    public void upload(@RequestAttribute Long tokenMemberId, @RequestBody OrderSaveDto dto) {
        orderService.save(tokenMemberId, dto);
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
