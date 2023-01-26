package taewan.Smart.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;

public interface ProductService {

    ProductInfoDto findOne(Long productId);
    Page<ProductInfoDto> findAll(Pageable pageable);
    Page<ProductInfoDto> findAllWithFilter(Pageable pageable, String code);
    Long save(ProductSaveDto productSaveDto);
    Long modify(ProductUpdateDto productUpdateDto);
    void delete(Long productId);
}
