package taewan.Smart.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.domain.order.dto.*;
import taewan.Smart.domain.order.repository.OrderItemDao;
import taewan.Smart.domain.order.repository.OrderRepository;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderService;
import taewan.Smart.domain.product.service.ProductService;
import taewan.Smart.fixture.ProductTestFixture;
import taewan.Smart.global.util.PropertyUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static taewan.Smart.fixture.MemberTestFixture.createMemberSaveDto;
import static taewan.Smart.fixture.OrderTestFixture.createOrderItemSaveDtoList;
import static taewan.Smart.fixture.OrderTestFixture.createOrderSaveDto;
import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.util.CustomFileUtils.deleteDirectory;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderIntegrationTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired private MemberService memberService;
    @Autowired private ProductService productService;
    @Autowired private DataSource dataSource;
    private Long validMemberId;

    @Value("${path.testImg}")
    private String testImgPath;

    @BeforeAll
    void beforeAllSetup() throws SQLException {
        init();
        validMemberId = memberService.save(createMemberSaveDto());
        ProductTestFixture
                .createProductSaveDtoList(10, testImgPath)
                .forEach(p -> productService.save(p));
    }

    @AfterAll
    void AfterAllDestroy() throws SQLException {
        init();
        deleteDirectory(PropertyUtils.getImgFolderPath() + "products");
    }

    private void init() throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stm = con.createStatement();
        stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        stm.executeUpdate("TRUNCATE members");
        stm.executeUpdate("TRUNCATE products");
        stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        stm.close();
        con.close();
    }

    //OrderServiceTest

    @Test
    @DisplayName("주문 저장 테스트")
    void save() {
        //given
        Long memberId = validMemberId;
        OrderSaveDto dto = createOrderSaveDto();

        //when
        orderService.save(memberId, dto);

        assertEquals(orderRepository.count(), 1L);
        assertEquals(orderItemDao.count(), dto.getOrderItemSaveDtoList().size());
    }

    @Test
    @DisplayName("등록되지 않은 회원 기본키를 포함하여 주문을 저장하는 경우 NoSuchElementException가 발생")
    void save_invalid_memberId() {
        //given
        Long memberId = 0L;
        OrderSaveDto dto = createOrderSaveDto();

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> orderService.save(memberId, dto));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
        assertEquals(orderRepository.count(), 0);
        assertEquals(orderItemDao.count(), 0);
    }

    @Test
    @DisplayName("등록되지 않은 제품 기본키를 포함하여 주문을 저장하는 경우 NoSuchElementException가 발생")
    void save_invalid_productId() {
        //given
        Long memberId = validMemberId;
        List<OrderItemSaveDto> itemSaveDtoList = createOrderItemSaveDtoList(1);

        itemSaveDtoList.add(new OrderItemSaveDto(0L, "s", 1));

        OrderSaveDto dto = new OrderSaveDto(itemSaveDtoList);

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> orderService.save(memberId, dto));
        assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
        assertEquals(orderRepository.count(), 0);
        assertEquals(orderItemDao.count(), 0);
    }

    @Test
    @DisplayName("회원 기본키와 제품 기본키가 모두 등록되지 않은 주문을 저장하는 경우 NoSuchElementException가 발생")
    void save_invalid_all() {
        //given
        Long memberId = 0L;
        List<OrderItemSaveDto> itemSaveDtoList = createOrderItemSaveDtoList(1);

        itemSaveDtoList.add(new OrderItemSaveDto(0L, "s", 1));

        OrderSaveDto dto = new OrderSaveDto(itemSaveDtoList);

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> orderService.save(memberId, dto));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
        assertEquals(orderRepository.count(), 0);
        assertEquals(orderItemDao.count(), 0);
    }

    @Test
    @DisplayName("회원 주문 전체 조회 테스트")
    void findAll() {
        //given
        Long memberId = validMemberId;
        OrderSaveDto dto = createOrderSaveDto();

        //when
        orderService.save(memberId, dto);
        orderService.save(memberId, dto);
        orderService.save(memberId, dto);
        assertEquals(orderRepository.count(), 3);

        //then
        assertEquals(orderRepository.countAllByMemberId(memberId), 3);
    }

    @Test
    @DisplayName("등록되지 않은 회원 기본키를 이용해 회원 주문 전체를 조회하는 경우 NoSuchElementException가 발생")
    void findAll_invalid_memberId() {
        //given
        Long memberId = 0L;

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> orderService.findAll(memberId));
        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
    }

    //OrderItemServiceTest

    @Test
    @DisplayName("주문의 특정 아이템을 취소하는 경우 해당 특정 목록의 배달상태가 '취소'로 변경")
    void cancel() {
        //given
        Long memberId = validMemberId;
        OrderSaveDto dto = createOrderSaveDto();
        orderService.save(memberId, dto);

        OrderItemInfoDto itemInfoDto = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);
        OrderItemCancelDto cancelDto = new OrderItemCancelDto(itemInfoDto.getOrderItemId(), "test");

        //when
        orderItemService.cancel(cancelDto);
        OrderItemInfoDto cancel = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);

        //then
        assertEquals(cancel.getDeliveryStatus(), "취소");
    }

    @Test
    @DisplayName("등록되지 않은 주문 아이템 기본키를 이용해 주문 아이템을 취소하는 경우 NoSuchElementException가 발생")
    void cancel_invalid_orderItemId() {
        //given
        OrderItemCancelDto cancelDto = new OrderItemCancelDto(0L, "test");

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> orderItemService.cancel(cancelDto));
        assertEquals(ex.getMessage(), ORDER_ITEM_NOT_FOUND.exception().getMessage());
    }

    @Test
    @DisplayName("주문의 특정 아이템을 환불하는 경우 해당 특정 목록의 배달상태가 '환불'로 변경")
    void refund() {
        //given
        Long memberId = validMemberId;
        OrderSaveDto dto = createOrderSaveDto();
        orderService.save(memberId, dto);

        OrderItemInfoDto itemInfoDto = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);
        OrderItemCancelDto cancelDto = new OrderItemCancelDto(itemInfoDto.getOrderItemId(), "test");

        //when
        orderItemService.refund(cancelDto);
        OrderItemInfoDto refund = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);

        //then
        assertEquals(refund.getDeliveryStatus(), "환불");
    }

    @Test
    @DisplayName("등록되지 않은 주문 아이템 기본키를 이용해 주문 아이템을 환불하는 경우 NoSuchElementException가 발생")
    void refund_invalid_orderItemId() {
        //given
        OrderItemCancelDto cancelDto = new OrderItemCancelDto(0L, "test");

        //when //then
        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
                () -> orderItemService.cancel(cancelDto));
        assertEquals(ex.getMessage(), ORDER_ITEM_NOT_FOUND.exception().getMessage());
    }
}
