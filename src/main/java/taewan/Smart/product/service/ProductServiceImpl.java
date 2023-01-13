package taewan.Smart.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductInfoDto findOne(Long productId) {
        return new ProductInfoDto(productRepository.findById(productId).orElseThrow());
    }

    @Override
    public List<ProductInfoDto> findAll() {
        return Arrays.asList(productRepository.findAll()
                .stream().map(ProductInfoDto::new)
                .toArray(ProductInfoDto[]::new));
    }

    @Override
    public Long save(ProductSaveDto productSaveDto) {
        return productRepository.save(new Product(productSaveDto)).getProductId();
    }

    @Transactional
    @Override
    public Long modify(ProductUpdateDto productUpdateDto) {
        Product found = productRepository.findById(productUpdateDto.getProductId()).orElseThrow();
        found.updateProduct(productUpdateDto);
        return found.getProductId();
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }
}
