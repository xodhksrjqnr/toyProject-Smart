package taewan.Smart.domain.product.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductDto {

    List<MultipartFile> getImgFiles();
    MultipartFile getDetailInfo();
    String getImgSavePath();
    default String getProductImgSavePath() {
        return getImgSavePath() + "/view";
    }
    default String getDetailInfoImgSavePath() {
        return getImgSavePath() + "/info";
    }
}
