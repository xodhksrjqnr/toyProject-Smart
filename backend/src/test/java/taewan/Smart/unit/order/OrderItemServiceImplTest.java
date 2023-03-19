package taewan.Smart.unit.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taewan.Smart.domain.order.dto.OrderItemCancelDto;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderItemDaoImpl;
import taewan.Smart.domain.order.service.OrderItemServiceImpl;
import taewan.Smart.domain.order.status.DeliveryStatus;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.fixture.OrderTestFixture;
import taewan.Smart.fixture.ProductTestFixture;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {
    @Mock private OrderItemDaoImpl orderItemDao;
    @InjectMocks private OrderItemServiceImpl orderItemService;

    @Test
    @DisplayName("주문 아이템 배송상태 취소로 변경 테스트")
    void cancel() {
        //given
        OrderItemCancelDto dto = new OrderItemCancelDto(1L, "etc");
        Product product = ProductTestFixture.createProduct();
        OrderItem orderItem = OrderItem.createOrderItem(
                OrderTestFixture.createOrderItemSaveDto(),
                product
        );

        when(orderItemDao.findById(any())).thenReturn(Optional.of(orderItem));

        //when
        orderItemService.cancel(dto);

        //then
        assertEquals(orderItem.getDeliveryStatus(), DeliveryStatus.CANCEL.toString());
        verify(orderItemDao, times(1)).findById(any());
    }

    @Test
    @DisplayName("존재하지 않는 주문 아이템 기본키를 이용해 배송상태 취소로 변경 시 NoSuchElementException가 발생")
    void cancel_invalid_orderItemId() {
        //given
        OrderItemCancelDto dto = new OrderItemCancelDto(1L, "etc");
        Product product = ProductTestFixture.createProduct();

        when(orderItemDao.findById(any())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> orderItemService.cancel(dto));
        verify(orderItemDao, times(1)).findById(any());
    }

    @Test
    @DisplayName("주문 아이템 배송상태 환불로 변경 테스트")
    void refund() {
        //given
        OrderItemCancelDto dto = new OrderItemCancelDto(1L, "etc");
        Product product = ProductTestFixture.createProduct();
        OrderItem orderItem = OrderItem.createOrderItem(
                OrderTestFixture.createOrderItemSaveDto(),
                product
        );

        when(orderItemDao.findById(any())).thenReturn(Optional.of(orderItem));

        //when
        orderItemService.refund(dto);

        //then
        assertEquals(orderItem.getDeliveryStatus(), DeliveryStatus.REFUND.toString());
        verify(orderItemDao, times(1)).findById(any());
    }

    @Test
    @DisplayName("존재하지 않는 주문 아이템 기본키를 이용해 배송상태 환불로 변경 시 NoSuchElementException가 발생")
    void refund_invalid_orderItemId() {
        //given
        OrderItemCancelDto dto = new OrderItemCancelDto(1L, "etc");
        Product product = ProductTestFixture.createProduct();

        when(orderItemDao.findById(any())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> orderItemService.refund(dto));
        verify(orderItemDao, times(1)).findById(any());
    }
}
