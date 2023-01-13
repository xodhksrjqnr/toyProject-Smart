package taewan.Smart.product.service;

import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;

import java.util.List;

public interface ProductService {

    ProductInfoDto findOne(Long productId);
    List<ProductInfoDto> findAll();
    Long save(ProductSaveDto productSaveDto);
    Long modify(ProductUpdateDto productUpdateDto);
    void delete(Long productId);
}