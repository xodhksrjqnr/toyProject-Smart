package taewan.Smart.unit.order;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import taewan.Smart.domain.order.entity.Order;
import taewan.Smart.domain.order.entity.OrderItem;
import taewan.Smart.domain.order.repository.OrderRepository;
import taewan.Smart.domain.product.entity.Product;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(ProductDaoImpl.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"taewan.Smart.domain.order.repository"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductDao productDao;
    @Autowired private DataSource dataSource;
    private Product product;

    @BeforeAll
    void beforeAllSetup() throws SQLException {
        init();
        product = ProductTestFixture.createProduct();
        productDao.save(product);
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
        stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
        stm.close();
        con.close();
    }

    @Test
    @DisplayName("회원 기본키와 일치하는 주문 전체 조회 테스트")
    void findAllByMemberId() {
        //given
        List<Order> orders = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            orders.add(
                Order.createOrder(
                    (long)((i % 2) + 1),
                    new ArrayList<>(){{
                        add(OrderItem.createOrderItem(
                            OrderItemTestFixture.createOrderItemSaveDto(product.getProductId()),
                            product
                        ));
                    }}
                )
            );
        }


        //when
        orderRepository.save(orders.get(0));
        orderRepository.save(orders.get(1));
        orderRepository.save(orders.get(2));

        //then
        assertEquals(orderRepository.findAllByMemberId(2L).size(), 2);
    }

    @Test
    @DisplayName("등록되지 않은 회원 기본키로 주문을 조회하는 경우 반환 리스트가 비어있음")
    void findAllByMemberId_invalid_memberId() {
        //given
        Long memberId = 0L;

        //when //then
        assertThat(orderRepository.findAllByMemberId(memberId)).isEmpty();
    }

    @Test
    @DisplayName("회원 기본키와 일치하는 주문 전체 개수 조회 테스트")
    void countAllByMemberId() {
        //given
        Long memberId = 1L;

        //when //then
        assertEquals(orderRepository.countAllByMemberId(memberId), 0);
        orderRepository.save(
                Order.createOrder(
                        memberId,
                        new ArrayList<>(){{
                            add(OrderItem.createOrderItem(
                                    OrderItemTestFixture.createOrderItemSaveDto(product.getProductId()),
                                    product
                            ));
                        }}
                )
        );
        assertEquals(orderRepository.countAllByMemberId(memberId), 1);
    }
}
