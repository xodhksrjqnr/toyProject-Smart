package taewan.Smart.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.repository.ProductRepository;

import static taewan.Smart.util.FileUtils.*;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
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

        return new ProductInfoDto(found, findImgFiles(found.getImgFolderPath(), root, address), address);
    }

    @Override
    public Page<ProductInfoDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(p -> new ProductInfoDto(p, findImgFiles(p.getImgFolderPath(), root, address), address));
    }

    @Override
    public Page<ProductInfoDto> findAllWithFilter(Pageable pageable, String code) {
        return productRepository.findByCodeContaining(pageable, code)
                .map(p -> new ProductInfoDto(p, findImgFiles(p.getImgFolderPath(), root, address), address));
    }

    @Transactional
    @Override
    public Long save(ProductSaveDto productSaveDto) {
        String path = "images/products/" + productSaveDto.getCode() + "/" + productSaveDto.getName();
        String infoFileName = path + "/" + saveImgFile(productSaveDto.getDetailInfo(), root + path);

        path += "/view";
        saveImgFiles(productSaveDto.getImgFiles(), root + path);
        return productRepository.save(new Product(productSaveDto, path, infoFileName)).getId();
    }

    @Transactional
    @Override
    public Long modify(ProductUpdateDto productUpdateDto) {
        Product found = productRepository.findById(productUpdateDto.getId()).orElseThrow();
        found.updateProduct(productUpdateDto, "tmp", "tmp");
        return found.getId();
    }

    @Override
    public void delete(Long productId) {
        Product found = productRepository.findById(productId).orElseThrow();
        String directoryPath = found.getImgFolderPath().replaceFirst("/view", "");

        deleteDirectory(directoryPath);
        productRepository.deleteById(productId);
    }
}