package taewan.Smart.unit.order;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderItemDao;
import taewan.Smart.domain.order.repository.OrderItemDaoImpl;
import taewan.Smart.domain.product.repository.ProductDao;
import taewan.Smart.domain.product.repository.ProductDaoImpl;
import taewan.Smart.fixture.OrderItemTestFixture;
import taewan.Smart.fixture.ProductTestFixture;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import({OrderItemDaoImpl.class, ProductDaoImpl.class})
@DataJpaTest
@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderItemDaoTest {

    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductDao productDao;
    @Autowired private DataSource dataSource;
    private static List<Long> productIds = new ArrayList<>();

    @BeforeAll
    void beforeAllSetup() throws SQLException {
        init();
        for (int i = 1; i <= 10; i++) {
            productIds.add(productDao.save(ProductTestFixture.createProduct(i)));
        }
    }

    @AfterAll
    void afterAllDestroy() throws SQLException {
        init();
    }

    private void init() throws SQLException {
        Connection con = dataSource.getConnection();
        Statement stm = con.createStatement();
        stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
        stm.executeUpdate("TRUNCATE products");
        stm.executeUpdate("TRUNCATE order_items");
        stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        stm.close();
        con.close();
    }

    @Test
    @DisplayName("주품 아이템 저장 테스트")
    void save() {
        //given
        Long productId = productIds.get(0);
        OrderItem orderItem = OrderItem.createOrderItem(
                OrderItemTestFixture.createOrderItemSaveDto(productId),
                productDao.findById(productId).get()
        );

        //when
        orderItemDao.save(orderItem);

        //then
        assertEquals(orderItemDao.count(), 1);
    }

    @Test
    @DisplayName("제품 기본키로 등록된 주문 아이템 존재 여부 테스트")
    void existsByProductId() {
        //given
        Long productId = productIds.get(0);
        OrderItem orderItem = OrderItem.createOrderItem(
                OrderItemTestFixture.createOrderItemSaveDto(productId),
                productDao.findById(productId).get()
        );

        //when
        orderItemDao.save(orderItem);

        //then
        assertThat(orderItemDao.existsByProductId(productId)).isTrue();
    }

    @Test
    @DisplayName("없는 제품 기본키로 주문 아이템 존재 여부 테스트")
    void existsByProductId_invalid_productId() {
        //given
        Long productId = 0L;

        //when //then
        assertThat(orderItemDao.existsByProductId(productId)).isFalse();
    }

    @Test
    @DisplayName("주문 아이템 기본키로 주문 아이템 조회 테스트")
    void findById() {
        //given
        Long productId = productIds.get(0);
        OrderItem orderItem = OrderItem.createOrderItem(
                OrderItemTestFixture.createOrderItemSaveDto(productId),
                productDao.findById(productId).get()
        );

        //when
        Long saveId = orderItemDao.save(orderItem);
        Optional<OrderItem> found = orderItemDao.findById(saveId);

        //then
        assertThat(found).isNotEmpty();
        assertEquals(OrderItemTestFixture.toString(found.get()), OrderItemTestFixture.toString(orderItem));
    }

    @Test
    @DisplayName("등록되지 않은 주문 아이템 기본키로 주문 아이템 조회 테스트")
    void findById_invalid_orderItemId() {
        //given
        Long productId = productIds.get(0);
        OrderItem orderItem = OrderItem.createOrderItem(
                OrderItemTestFixture.createOrderItemSaveDto(productId),
                productDao.findById(productId).get()
        );

        //when
        Long saveId = orderItemDao.save(orderItem);
        Optional<OrderItem> found = orderItemDao.findById(saveId);

        //then
        assertThat(found).isNotEmpty();
        assertEquals(OrderItemTestFixture.toString(found.get()), OrderItemTestFixture.toString(orderItem));
    }

    @Test
    @DisplayName("DB에 저장된 전체 주문 아이템 개수 조회 테스트")
    void count() {
        //given
        Long productId = productIds.get(0);
        OrderItem orderItem = OrderItem.createOrderItem(
                OrderItemTestFixture.createOrderItemSaveDto(productId),
                productDao.findById(productId).get()
        );

        //when //then
        assertEquals(orderItemDao.count(), 0);
        orderItemDao.save(orderItem);
        assertEquals(orderItemDao.count(), 1);
    }
}
