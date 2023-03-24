package taewan.Smart.domain.product.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class ProductDto {

    public abstract List<MultipartFile> getImgFiles();
    public abstract MultipartFile getDetailInfo();
    abstract String getImgSavePath();
    public String getProductImgSavePath() {
        return getImgSavePath() + "/view";
    }
    public String getDetailInfoImgSavePath() {
        return getImgSavePath() + "/info";
    }
}
