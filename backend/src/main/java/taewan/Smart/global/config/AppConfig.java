package taewan.Smart.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;
import taewan.Smart.domain.category.service.CategoryService;
import taewan.Smart.domain.category.service.CategoryServiceImpl;
import taewan.Smart.domain.member.repository.MemberRepository;
import taewan.Smart.domain.member.service.MemberCertificationService;
import taewan.Smart.domain.member.service.MemberCertificationServiceImpl;
import taewan.Smart.domain.member.service.MemberService;
import taewan.Smart.domain.member.service.MemberServiceImpl;
import taewan.Smart.domain.order.repository.OrderItemDao;
import taewan.Smart.domain.order.repository.OrderItemDaoImpl;
import taewan.Smart.domain.order.repository.OrderRepository;
import taewan.Smart.domain.order.service.OrderItemService;
import taewan.Smart.domain.order.service.OrderItemServiceImpl;
import taewan.Smart.domain.order.service.OrderService;
import taewan.Smart.domain.order.service.OrderServiceImpl;
import taewan.Smart.domain.product.repository.ProductDao;
import taewan.Smart.domain.product.repository.ProductDaoImpl;
import taewan.Smart.domain.product.service.ProductService;
import taewan.Smart.domain.product.service.ProductServiceImpl;

import javax.persistence.EntityManager;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class AppConfig {

    private final EntityManager entityManager;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;
    private final OrderRepository orderRepository;

    @Bean
    public ProductDao productDao() {
        return new ProductDaoImpl(this.entityManager);
    }

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl(productDao(), orderItemDao());
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(this.memberRepository);
    }

    @Bean
    public MemberCertificationService memberCertificationService() {
        return new MemberCertificationServiceImpl(this.memberRepository);
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryServiceImpl(this.categoryRepository, this.categoryItemRepository);
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                this.orderRepository, this.memberRepository, productDao()
        );
    }

    @Bean
    public OrderItemDao orderItemDao() {
        return new OrderItemDaoImpl(this.entityManager);
    }

    @Bean
    public OrderItemService orderItemService() {
        return new OrderItemServiceImpl(orderItemDao());
    }
}
