package taewan.Smart.product.service;

import org.springframework.data.domain.Page;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;

import java.util.List;

public interface ProductService {

    ProductInfoDto findOne(Long productId);
    List<ProductInfoDto> findAll(Integer page);
    Page<ProductInfoDto> findAllWithFilter(Integer page, String code, Long option, Integer size);
    Long save(ProductSaveDto productSaveDto);
    Long modify(ProductUpdateDto productUpdateDto);
    void delete(Long productId);
}
