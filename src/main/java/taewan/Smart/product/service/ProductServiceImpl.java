package taewan.Smart.product.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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

        return new ProductInfoDto(found, findImgFiles(found.getImgFolderPath()));
    }

    @Override
    public List<ProductInfoDto> findAll(Integer page) {
        List<Product> found = productRepository.findAll();
        List<ProductInfoDto> converted = new ArrayList<>(found.size());

        found.forEach(p -> converted.add(new ProductInfoDto(p, findImgFiles(p.getImgFolderPath()))));
        return converted;
    }

    @Override
    public List<ProductInfoDto> findAllWithFilter(Integer page, String code, Long option, Integer size) {
        PageRequest condition = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate", "price"));
        List<Product> found = productRepository.findByCodeContaining(code, condition);
        List<ProductInfoDto> converted = new ArrayList<>(found.size());

        found.forEach(p -> converted.add(new ProductInfoDto(p, findImgFiles(p.getImgFolderPath()))));
        return converted;
    }

    @Transactional
    @Override
    public Long save(ProductSaveDto productSaveDto) {
        String path = resourceSavePath + "images/products/" + productSaveDto.getCode() + "/" + productSaveDto.getName();
        String infoFileName = path + "/" + saveImgFile(productSaveDto.getDetailInfo(), path);

        saveImgFiles(productSaveDto.getImgFiles(), path + "/view");
        return productRepository.save(new Product(productSaveDto, path + "/view", infoFileName)).getId();
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

    private List<String> findImgFiles(String directoryPath) {
        List<String> found = new ArrayList<>();
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();

        for (File f : files)
            if (f.isFile())
                found.add(directoryPath + "/" + f.getName());
        return found;
    }

    private void saveImgFiles(List<MultipartFile> files, String path) {
        files.forEach(f -> saveImgFile(f, path));
    }

    private String saveImgFile(MultipartFile file, String path) {
        String extension = file.getOriginalFilename().replaceFirst(".*\\.", ".");
        String uploadName = UUID.randomUUID().toString() + extension;

        try {
            Files.createDirectories(Paths.get(path));
            file.transferTo(new File(path, uploadName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uploadName;
    }

    private void deleteDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();

        try {
            for (File f : files) {
                String target = directoryPath + "/" + f.getName();
                if (f.isDirectory())
                    deleteDirectory(target);
                Files.deleteIfExists(Paths.get(target));
            }
            Files.deleteIfExists(Paths.get(directoryPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}