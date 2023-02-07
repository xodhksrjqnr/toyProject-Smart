package taewan.Smart.domain.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.domain.product.repository.ProductRepository;

import java.util.Optional;

import static taewan.Smart.global.error.ExceptionStatus.PRODUCT_NAME_DUPLICATE;
import static taewan.Smart.global.error.ExceptionStatus.PRODUCT_NOT_FOUND;
import static taewan.Smart.global.util.FileUtils.*;

@Transactional(readOnly = true)
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Value("${root.path}")
    private String root;
    @Value("${server.address.basic}")
    private String address;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductInfoDto findOne(Long productId) {
        Product found = productRepository.findById(productId).orElseThrow();

        return new ProductInfoDto(found, findFiles(found.getImgFolderPath(), root, address), address);
    }

    @Override
    public Page<ProductInfoDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(p -> new ProductInfoDto(p, findFiles(p.getImgFolderPath(), root, address), address));
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
        return found.map(p -> new ProductInfoDto(p, findFiles(p.getImgFolderPath(), root, address), address));
    }

    @Transactional
    @Override
    public Long save(ProductSaveDto productSaveDto) {
        if (!productRepository.findByName(productSaveDto.getName()).isEmpty()) {
            throw PRODUCT_NAME_DUPLICATE.exception();
        }

        String[] paths = saveImgFile(productSaveDto);

        return productRepository.save(new Product(productSaveDto, paths[0], paths[1])).getId();
    }

    @Transactional
    @Override
    public Long modify(ProductUpdateDto productUpdateDto) {
        Optional<Product> equalNameProduct = productRepository.findByName(productUpdateDto.getName());

        if (equalNameProduct.isPresent() && !equalNameProduct.get().getId().equals(productUpdateDto.getId())) {
            throw PRODUCT_NAME_DUPLICATE.exception();
        }

        Product found = productRepository.findById(productUpdateDto.getId()).orElseThrow();
        String directoryPath = root + found.getImgFolderPath().replaceFirst("/view", "");

        deleteDirectory(directoryPath);

        String[] paths = saveImgFile(productUpdateDto);

        found.updateProduct(productUpdateDto, paths[0], paths[1]);
        return found.getId();
    }

    private String[] saveImgFile(ProductSaveDto dto) {
        String[] paths = new String[2];

        paths[0] = "images/products/" + dto.getCode() + "/" + dto.getName();
        paths[1] = paths[0] + "/" + saveFile(dto.getDetailInfo(), root + paths[0]);
        paths[0] += "/view";
        saveFiles(dto.getImgFiles(), root + paths[0]);
        return paths;
    }

    @Transactional
    @Override
    public void delete(Long productId) {
        Product found = productRepository.findById(productId)
                .orElseThrow(PRODUCT_NOT_FOUND::exception);
        String directoryPath = root + found.getImgFolderPath().replaceFirst("/view", "");

        deleteDirectory(directoryPath);
        productRepository.deleteById(productId);
    }
}