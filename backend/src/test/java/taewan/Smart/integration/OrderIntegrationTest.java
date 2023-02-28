package taewan.Smart.integration;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static taewan.Smart.fixture.OrderTestFixture.createOrderItemSaveDtoList;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderIntegrationTest {

//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private OrderItemService orderItemService;
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//    @Autowired private MemberService memberService;
//    @Autowired private ProductDao productDao;
//    @Autowired private DataSource dataSource;
//    private Long validMemberId;
//
//    @BeforeAll
//    void beforeAllSetup() throws SQLException {
//        init();
//        validMemberId = memberService.save(getMemberSaveDtoList().get(0));
//        List<Product> products = new ArrayList<>();
//        getProductSaveDtoList().forEach(p -> products.add(p.toEntity(saveImgFile(p))));
//        productDao.saveAll(products);
//    }
//
//    @AfterAll
//    void AfterAllDestroy() throws SQLException {
//        init();
//        deleteDirectory("products/");
//    }
//
//    private void init() throws SQLException {
//        Connection con = dataSource.getConnection();
//        Statement stm = con.createStatement();
//        stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
//        stm.executeUpdate("TRUNCATE member");
//        stm.executeUpdate("TRUNCATE product");
//        stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
//        stm.close();
//        con.close();
//    }
//
//    //OrderServiceTest
//
//    @Test
//    void 주문_저장_테스트() {
//        //given
//        Long memberId = validMemberId;
//        OrderSaveDto dto = getOrderSaveDto();
//
//        //when
//        orderService.save(memberId, dto);
//
//        assertEquals(orderRepository.count(), 1L);
//        assertEquals(orderItemRepository.count(), dto.getOrderItemSaveDtoList().size());
//    }
//
//    @Test
//    void 없는_memberId를_포함한_주문_저장_테스트() {
//        //given
//        Long memberId = 0L;
//        OrderSaveDto dto = getOrderSaveDto();
//
//        //when //then
//        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//                () -> orderService.save(memberId, dto));
//        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
//        assertEquals(orderRepository.count(), 0);
//        assertEquals(orderItemRepository.count(), 0);
//    }
//
//    @Test
//    void 없는_productId를_포함한_주문_저장_테스트() {
//        //given
//        Long memberId = validMemberId;
//        List<OrderItemSaveDto> itemSaveDtoList = createOrderItemSaveDtoList(1);
//
//        itemSaveDtoList.get(0).setProductId(0L);
//
//        OrderSaveDto dto = new OrderSaveDto(itemSaveDtoList);
//
//        //when //then
//        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//                () -> orderService.save(memberId, dto));
//        assertEquals(ex.getMessage(), PRODUCT_NOT_FOUND.exception().getMessage());
//        assertEquals(orderRepository.count(), 0);
//        assertEquals(orderItemRepository.count(), 0);
//    }
//
//    @Test
//    void 없는_memberId와_없는_productId를_포함한_주문_저장_테스트() {
//        //given
//        Long memberId = 0L;
//        List<OrderItemSaveDto> itemSaveDtoList = createOrderItemSaveDtoList(1);
//
//        itemSaveDtoList.get(0).setProductId(0L);
//
//        OrderSaveDto dto = new OrderSaveDto(itemSaveDtoList);
//
//        //when //then
//        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//                () -> orderService.save(memberId, dto));
//        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
//        assertEquals(orderRepository.count(), 0);
//        assertEquals(orderItemRepository.count(), 0);
//    }
//
//    @Test
//    void 회원_주문_전체_조회() {
//        //given
//        Long memberId = validMemberId;
//        OrderSaveDto dto = getOrderSaveDto();
//
//        //when
//        orderService.save(memberId, dto);
//        orderService.save(memberId, dto);
//        orderService.save(memberId, dto);
//        assertEquals(orderRepository.count(), 3);
//        List<OrderInfoDto> orderList = orderService.findAll(memberId);
//
//        //then
//        assertEquals(orderList.size(), 3);
//    }
//
//    @Test
//    void 없는_memberId를_이용한_회원_주문_전체_조회() {
//        //given
//        Long memberId = 0L;
//
//        //when //then
//        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//                () -> orderService.findAll(memberId));
//        assertEquals(ex.getMessage(), MEMBER_NOT_FOUND.exception().getMessage());
//    }
//
//    //OrderItemServiceTest
//
//    @Test
//    void 주문_목록_취소_테스트() {
//        //given
//        Long memberId = validMemberId;
//        OrderSaveDto dto = getOrderSaveDto();
//        orderService.save(memberId, dto);
//
//        OrderItemInfoDto itemInfoDto = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);
//        OrderItemCancelDto cancelDto = OrderItemCancelDto.builder()
//                .orderItemId(itemInfoDto.getOrderItemId())
//                .reason("test")
//                .build();
//
//        //when
//        orderItemService.cancel(cancelDto);
//        OrderItemInfoDto cancel = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);
//
//        //then
//        assertEquals(cancel.getDeliveryStatus(), "CANCEL");
//    }
//
//    @Test
//    void 없는_주문_목록_취소_테스트() {
//        //given
//        OrderItemCancelDto cancelDto = OrderItemCancelDto.builder()
//                .orderItemId(0L)
//                .reason("test")
//                .build();
//
//        //when //then
//        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//                () -> orderItemService.cancel(cancelDto));
//        assertEquals(ex.getMessage(), ORDER_ITEM_NOT_FOUND.exception().getMessage());
//    }
//
//    @Test
//    void 주문_목록_환불_테스트() {
//        //given
//        Long memberId = validMemberId;
//        OrderSaveDto dto = getOrderSaveDto();
//        orderService.save(memberId, dto);
//
//        OrderItemInfoDto itemInfoDto = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);
//        OrderItemCancelDto cancelDto = OrderItemCancelDto.builder()
//                .orderItemId(itemInfoDto.getOrderItemId())
//                .reason("test")
//                .build();
//
//        //when
//        orderItemService.refund(cancelDto);
//        OrderItemInfoDto refund = orderService.findAll(memberId).get(0).getOrderItemInfoDtoList().get(0);
//
//        //then
//        assertEquals(refund.getDeliveryStatus(), "REFUND");
//    }
//
//    @Test
//    void 없는_주문_목록_환불_테스트() {
//        //given
//        OrderItemCancelDto cancelDto = OrderItemCancelDto.builder()
//                .orderItemId(0L)
//                .reason("test")
//                .build();
//
//        //when //then
//        NoSuchElementException ex = assertThrows(NoSuchElementException.class,
//                () -> orderItemService.cancel(cancelDto));
//        assertEquals(ex.getMessage(), ORDER_ITEM_NOT_FOUND.exception().getMessage());
//    }
}
