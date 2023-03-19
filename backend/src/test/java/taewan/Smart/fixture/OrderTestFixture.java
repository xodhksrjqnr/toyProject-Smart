package taewan.Smart.fixture;

import taewan.Smart.domain.order.dto.OrderItemSaveDto;
import taewan.Smart.domain.order.dto.OrderSaveDto;

import java.util.ArrayList;
import java.util.List;

public class OrderTestFixture {

    private static final String[] size = {"s", "m", "l", "xl", "xxl"};

    public static OrderSaveDto createOrderSaveDto() {
        OrderSaveDto dto = new OrderSaveDto(createOrderItemSaveDtoList());

        return dto;
    }

    public static OrderItemSaveDto createOrderItemSaveDto() {
        return createOrderItemSaveDto(1);
    }

    public static OrderItemSaveDto createOrderItemSaveDto(int index) {
        OrderItemSaveDto dto = new OrderItemSaveDto((long)(index + 1), size[index % 5], index % 5);

        return dto;
    }

    public static List<OrderItemSaveDto> createOrderItemSaveDtoList() {
        return createOrderItemSaveDtoList(5);
    }

    public static List<OrderItemSaveDto> createOrderItemSaveDtoList(int size) {
        List<OrderItemSaveDto> list = new ArrayList<>();

        for (int i = 0; i < size; i++)
            list.add(createOrderItemSaveDto(i));
        return list;
    }
}
