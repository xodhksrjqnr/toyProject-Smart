package taewan.Smart.unit.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.order.dto.OrderSaveDto;
import taewan.Smart.domain.order.entity.Order;
import taewan.Smart.domain.order.repository.OrderRepository;
import taewan.Smart.domain.order.service.OrderServiceImpl;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductDaoImpl;
import taewan.Smart.fixture.OrderTestFixture;
import taewan.Smart.fixture.ProductTestFixture;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private ProductDaoImpl productDao;
    @InjectMocks private OrderServiceImpl orderService;

    @Test
    @DisplayName("주문 저장 테스트")
    void save() {
        //given
        OrderSaveDto dto = OrderTestFixture.createOrderSaveDto();
        Product product = ProductTestFixture.createProduct();
        Order order = Order.createOrder(1L, new ArrayList<>());

        when(memberRepository.existsById(any())).thenReturn(true);
        when(productDao.findById(any())).thenReturn(Optional.of(product));
        when(orderRepository.save(any())).thenReturn(order);

        //when
        orderService.save(1L, dto);

        //then
        verify(memberRepository, times(1)).existsById(any());
        verify(productDao, times(dto.getOrderItemSaveDtoList().size())).findById(any());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("존재하지 않는 회원 기본키를 포함할 때 주문 저장 시 NoSuchElementException가 발생")
    void save_invalid_memberId() {
        //given
        OrderSaveDto dto = OrderTestFixture.createOrderSaveDto();

        when(memberRepository.existsById(any())).thenReturn(false);

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> orderService.save(0L, dto));
        verify(memberRepository, only()).existsById(any());
        verify(memberRepository, times(1)).existsById(any());
    }

    @Test
    @DisplayName("존재하지 않는 제품 기본키를 포함할 때 주문 저장 시 NoSuchElementException가 발생")
    void save_invalid_productId() {
        //given
        OrderSaveDto dto = OrderTestFixture.createOrderSaveDto();

        when(memberRepository.existsById(any())).thenReturn(true);
        when(productDao.findById(any())).thenReturn(Optional.empty());

        //when //then
        assertThrows(NoSuchElementException.class,
                () -> orderService.save(1L, dto));
        verify(memberRepository, times(1)).existsById(any());
        verify(productDao, times(1)).findById(any());
    }
}
