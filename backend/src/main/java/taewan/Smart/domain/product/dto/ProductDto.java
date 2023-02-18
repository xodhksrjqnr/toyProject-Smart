package taewan.Smart.domain.product.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductDto {

    String getDirectoryPath();
    String getViewPath();
    List<MultipartFile> getImgFiles();
    MultipartFile getDetailInfo();
}
