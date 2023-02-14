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
import taewan.Smart.global.config.properties.AddressProperties;
import taewan.Smart.global.config.properties.PathProperties;

import static taewan.Smart.global.error.ExceptionStatus.*;
import static taewan.Smart.global.util.FileUtils.*;

@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final String ADDRESS;
    private final String ROOT;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PathProperties pathProperties,
                              AddressProperties addressProperties) {
        this.productRepository = productRepository;
        this.ADDRESS = addressProperties.getServer();
        this.ROOT = pathProperties.getHome();
    }

    @Override
    public ProductInfoDto findOne(Long productId) {
        Product found = productRepository.findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        return new ProductInfoDto(found, findFiles(found.getImgFolderPath(), ROOT, ADDRESS), ADDRESS);
    }

    @Override
    public Page<ProductInfoDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(p -> new ProductInfoDto(p, findFiles(p.getImgFolderPath(), ROOT, ADDRESS), ADDRESS));
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
        return found.map(p -> new ProductInfoDto(p, findFiles(p.getImgFolderPath(), ROOT, ADDRESS), ADDRESS));
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
        deleteDirectory(ROOT + found.getDirectoryPath());
        found.updateProduct(dto, dto.getViewPath(), saveImgFile(dto));
        return found.getProductId();
    }

    private String saveImgFile(ProductSaveDto dto) {
        try {
            saveFiles(dto.getImgFiles(), ROOT + dto.getViewPath());
            return saveFile(dto.getDetailInfo(), ROOT + dto.getDirectoryPath());
        } catch (NullPointerException e) {
            throw PRODUCT_IMAGE_EMPTY.exception();
        }
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        Product found = productRepository.findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);

        deleteDirectory(ROOT + found.getDirectoryPath());
        productRepository.deleteById(productId);
    }
}