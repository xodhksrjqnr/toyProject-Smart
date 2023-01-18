package taewan.Smart.product.service;

import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;

import java.util.List;

public interface ProductService {

    ProductInfoDto findOne(Long productId);
    List<ProductInfoDto> findAll(Integer page);
    List<ProductInfoDto> findAllWithFilter(Integer page, String code, Long option, Integer size);
    Long save(ProductSaveDto productSaveDto);
    Long modify(ProductUpdateDto productUpdateDto);
    void delete(Long productId);
}
