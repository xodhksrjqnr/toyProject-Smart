package taewan.Smart.global.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import taewan.Smart.domain.product.repository.ProductRepository;
import taewan.Smart.domain.product.service.ProductService;
import taewan.Smart.domain.product.service.ProductServiceImpl;

@Configuration
@EnableJpaAuditing
public class AppConfig {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final MemberCertificationService memberCertificationService;
    private final CategoryRepository categoryRepository;
    private final CategoryItemRepository categoryItemRepository;

    @Autowired
    public AppConfig(ProductRepository productRepository, MemberRepository memberRepository,
                     MemberCertificationService memberCertificationService,
                     CategoryRepository categoryRepository, CategoryItemRepository categoryItemRepository) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.memberCertificationService = memberCertificationService;
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
    public MemberCertificationService memberCertificationService() {
        return new MemberCertificationServiceImpl(this.memberRepository);
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryServiceImpl(this.categoryRepository, this.categoryItemRepository);
    }
}
