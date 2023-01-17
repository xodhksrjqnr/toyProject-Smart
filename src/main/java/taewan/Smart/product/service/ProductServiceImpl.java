package taewan.Smart.product.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.repository.ProductRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    @Value("${resource.save.path}")
    private String resourceSavePath;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductInfoDto findOne(Long productId) {
        Product found = productRepository.findById(productId).orElseThrow();
        List<String> imgFiles = new ArrayList<>();
        File dir = new File(found.getImgFolderPath());
        File[] files = dir.listFiles();
        for (File file : files)
            if (file.isFile())
                imgFiles.add(found.getImgFolderPath() + "/" + file.getName());
        return new ProductInfoDto(found, imgFiles);
    }

    @Override
    public List<ProductInfoDto> findAll() {
        List<Product> found = productRepository.findAll();
        List<ProductInfoDto> converted = new ArrayList<>();
        for (Product p : found) {
            List<String> imgFiles = new ArrayList<>();
            File dir = new File(p.getImgFolderPath());
            File[] files = dir.listFiles();
            for (File file : files)
                if (file.isFile())
                    imgFiles.add(p.getImgFolderPath() + "/" + file.getName());
            converted.add(new ProductInfoDto(p, imgFiles));
        }
        return converted;
    }

    @Transactional
    @Override
    public Long save(ProductSaveDto productSaveDto) {
        String path = resourceSavePath + "images/products/" + productSaveDto.getCode() + "/" + productSaveDto.getName();
        String infoFileName = "";
        try {
            Files.createDirectories(Paths.get(path + "/view"));
            productSaveDto.getImgFiles().forEach(file -> {
                try {
                    String extension = file.getOriginalFilename().replaceFirst(".*\\.", ".");
                    String uploadName = UUID.randomUUID().toString() + extension;
                    file.transferTo(new File(path + "/view", uploadName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            String extension = productSaveDto.getDetailInfo().getOriginalFilename().replaceFirst(".*\\.", ".");
            String uploadName = UUID.randomUUID().toString() + extension;
            productSaveDto.getDetailInfo().transferTo(new File(path, uploadName));
            infoFileName = uploadName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return productRepository.save(new Product(productSaveDto, path + "/view", path + "/" + infoFileName)).getId();
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
        try {
            File dir = new File(found.getImgFolderPath());
            File[] files = dir.listFiles();
            for (File f : files)
                Files.deleteIfExists(Paths.get(found.getImgFolderPath() + "/" + f.getName()));
            Files.deleteIfExists(Paths.get(found.getImgFolderPath()));
            Files.deleteIfExists(Paths.get(found.getDetailInfo()));
            Files.deleteIfExists(Paths.get(found.getImgFolderPath().replaceFirst("/view", "")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productRepository.deleteById(productId);
    }
}
