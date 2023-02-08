package taewan.Smart.domain.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;

public interface ProductService {

    ProductInfoDto findOne(Long productId);
    Page<ProductInfoDto> findAll(Pageable pageable);
    Page<ProductInfoDto> findAllWithFilter(Pageable pageable, String code, String search);
    Long save(ProductSaveDto productSaveDto);
    Long update(ProductUpdateDto productUpdateDto);
    void delete(Long productId);
}
