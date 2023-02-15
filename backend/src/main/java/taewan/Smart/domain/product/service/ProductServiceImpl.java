package taewan.Smart.domain.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductRepository;

import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.utils.FileUtil.*;
import static taewan.Smart.global.utils.PropertyUtil.*;

@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductInfoDto findOne(Long productId) {
        Product found = productRepository.findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        return new ProductInfoDto(found, findFiles(found.getImgFolderPath(), ROOT_PATH, SERVER_ADDRESS), SERVER_ADDRESS);
    }

    @Override
    public Page<ProductInfoDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(p -> new ProductInfoDto(p, findFiles(p.getImgFolderPath(), ROOT_PATH, SERVER_ADDRESS), SERVER_ADDRESS));
    }

    @Override
    public Page<ProductInfoDto> findAllWithFilter(Pageable pageable, String code, String search) {
        Page<Product> found;

        if (!search.isEmpty() && !code.isEmpty()) {
            found = productRepository.findAllByCodeContainsAndNameContains(pageable, code, search);
        } else if (search.isEmpty()) {
            found = productRepository.findAllByCodeContains(pageable, code);
        } else {
            found = productRepository.findAllByNameContains(pageable, search);
        }
        return found.map(p -> new ProductInfoDto(p, findFiles(p.getImgFolderPath(), ROOT_PATH, SERVER_ADDRESS), SERVER_ADDRESS));
    }

    @Transactional
    @Override
    public Long save(ProductSaveDto dto) {
        productRepository.findByName(dto.getName())
                .ifPresent(p -> {throw PRODUCT_NAME_DUPLICATE.exception();});
        return productRepository.save(dto.toEntity(dto.getViewPath(), saveImgFile(dto)))
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
        deleteDirectory(ROOT_PATH + found.getDirectoryPath());
        found.updateProduct(dto, dto.getViewPath(), saveImgFile(dto));
        return found.getProductId();
    }

    private String saveImgFile(ProductSaveDto dto) {
        try {
            saveFiles(dto.getImgFiles(), ROOT_PATH + dto.getViewPath());
            return saveFile(dto.getDetailInfo(), ROOT_PATH + dto.getDirectoryPath());
        } catch (NullPointerException e) {
            throw PRODUCT_IMAGE_EMPTY.exception();
        }
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        Product found = productRepository.findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        deleteDirectory(ROOT_PATH + found.getDirectoryPath());
        productRepository.deleteById(productId);
    }
}