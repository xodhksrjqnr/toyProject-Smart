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
import taewan.Smart.domain.product.repository.ProductRepository;

import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.utils.FileUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public ProductInfoDto findOne(Long productId) {
        Product found = productRepository.findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        return convertInfoDto(found);
    }

    @Override
    public Page<ProductInfoDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::convertInfoDto);
    }

    @Override
    public Page<ProductInfoDto> findAllWithFilter(Pageable pageable, String code, String search) {
        Page<Product> found;

        if (!search.isEmpty() && !code.isEmpty()) {
            found = productRepository.findAllByCodeContainsAndNameContains(pageable, code, search);
        } else if (search.isEmpty() && code.isEmpty()) {
            found = productRepository.findAll(pageable);
        } else if (search.isEmpty()) {
            found = productRepository.findAllByCodeContains(pageable, code);
        } else {
            found = productRepository.findAllByNameContains(pageable, search);
        }
        return found.map(this::convertInfoDto);
    }

    @Transactional
    @Override
    public Long save(ProductSaveDto dto) {
        productRepository.findByName(dto.getName())
                .ifPresent(p -> {throw PRODUCT_NAME_DUPLICATE.exception();});
        return productRepository.save(dto.toEntity(saveImgFile(dto)))
                .getProductId();
    }

    @Transactional
    @Override
    public Long update(ProductUpdateDto dto) {
        Product found = productRepository.findById(dto.getProductId())
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        productRepository.findByName(dto.getName())
                .ifPresent(p -> {
                    if (!p.getProductId().equals(dto.getProductId())) {
                        throw PRODUCT_NAME_DUPLICATE.exception();
                    }
                });
        deleteDirectory(found.getDirectoryPath());
        found.updateProduct(dto, saveImgFile(dto));
        return found.getProductId();
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        Product found = productRepository.findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        if (orderItemRepository.existsByProductId(productId))
            throw PRODUCT_REFERRED.exception();
        productRepository.deleteById(productId);
        deleteDirectory(found.getDirectoryPath());
    }

    private String saveImgFile(ProductDto dto) {
        try {
            saveFiles(dto.getImgFiles(), dto.getViewPath());
            return saveFile(dto.getDetailInfo(), dto.getDirectoryPath());
        } catch (NullPointerException e) {
            throw PRODUCT_IMAGE_EMPTY.exception();
        }
    }

    private ProductInfoDto convertInfoDto(Product product) {
        return new ProductInfoDto(
                product,
                getAccessUrls(product.getImgFolderPath()),
                getAccessUrl(product.getDetailInfo())
        );
    }
}