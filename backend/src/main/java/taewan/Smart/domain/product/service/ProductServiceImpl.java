package taewan.Smart.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.order.repository.OrderItemRepository;
import taewan.Smart.domain.product.dto.ProductDto;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductDao;

import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.utils.FileUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ProductInfoDto findOne(Long productId) {
        return productDao
                .findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception)
                .toInfoDto();
    }

    @Override
    public Page<ProductInfoDto> findAllByFilter(Pageable pageable, String code, String search) {
        return productDao
                .findAllByFilter(pageable, code, search)
                .map(Product::toInfoDto);
    }

    @Transactional
    @Override
    public Long save(ProductSaveDto dto) {
        if (productDao.existsByName(dto.getName())) {
            throw PRODUCT_NAME_DUPLICATE.exception();
        }
        saveImgFile(dto);
        return productDao.save(dto.toEntity());
    }

    @Transactional
    @Override
    public Long update(ProductUpdateDto dto) {
        Product found = productDao
                .findById(dto.getProductId())
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        if (productDao.existsByNameAndProductIdNot(dto.getProductId(), dto.getName())) {
            throw PRODUCT_NAME_DUPLICATE.exception();
        }

        String preImgPath = found.getImgPath();

        found.updateProduct(dto);
        deleteDirectory(preImgPath);
        saveImgFile(dto);
        return found.getProductId();
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        Product found = productDao
                .findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        if (orderItemRepository.existsByProductId(productId)) {
            throw PRODUCT_REFERRED.exception();
        }
        productDao.deleteById(productId);
        deleteDirectory(found.getImgPath());
    }

    private void saveImgFile(ProductDto dto) {
        try {
            saveFiles(dto.getImgFiles(), dto.getProductImgSavePath());
            saveFile(dto.getDetailInfo(), dto.getDetailInfoImgSavePath());
        } catch (NullPointerException e) {
            throw PRODUCT_IMAGE_EMPTY.exception();
        }
    }
}