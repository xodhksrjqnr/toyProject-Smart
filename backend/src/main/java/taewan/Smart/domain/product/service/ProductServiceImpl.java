package taewan.Smart.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.order.repository.OrderItemDao;
import taewan.Smart.domain.product.dto.ProductDto;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductDao;
import taewan.Smart.global.converter.PathConverter;

import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.util.CustomFileUtils.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final OrderItemDao orderItemDao;

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

        Long savedId = productDao.save(dto.toEntity());

        saveImgFile(dto);
        return savedId;
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
        deleteDirectory(PathConverter.toImgAccessLocal(preImgPath));
        saveImgFile(dto);
        return found.getProductId();
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        Product found = productDao
                .findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        if (orderItemDao.existsByProductId(productId)) {
            throw PRODUCT_REFERRED.exception();
        }
        productDao.deleteById(productId);
        deleteDirectory(PathConverter.toImgAccessLocal(found.getImgPath()));
    }

    private void saveImgFile(ProductDto dto) {
        try {
            saveFiles(dto.getImgFiles(), PathConverter.toImgAccessLocal(dto.getProductImgSavePath()));
            saveFile(dto.getDetailInfo(), PathConverter.toImgAccessLocal(dto.getDetailInfoImgSavePath()));
        } catch (IllegalArgumentException ex) {
            throw PRODUCT_IMAGE_EMPTY.exception();
        }
    }
}