package taewan.Smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import taewan.Smart.category.repository.CategoryItemRepository;
import taewan.Smart.category.repository.CategoryRepository;
import taewan.Smart.category.service.CategoryService;
import taewan.Smart.category.service.CategoryServiceImpl;
import taewan.Smart.member.repository.MemberRepository;
import taewan.Smart.member.service.MemberService;
import taewan.Smart.member.service.MemberServiceImpl;
import taewan.Smart.product.repository.ProductRepository;
import taewan.Smart.product.service.ProductService;
import taewan.Smart.product.service.ProductServiceImpl;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    @Autowired
    public AppConfig(ProductRepository productRepository, MemberRepository memberRepository, CategoryRepository categoryRepository, CategoryItemRepository categoryItemRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
        this.categoryItemRepository = categoryItemRepository;
    }

    @Bean
    public ProductService productService() {
        return new ProductServiceImpl(this.productRepository);
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(this.memberRepository);
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryServiceImpl(this.categoryRepository, this.categoryItemRepository);
    }
}
