package taewan.Smart.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.product.dto.ProductInfoDto;
import taewan.Smart.product.dto.ProductSaveDto;
import taewan.Smart.product.dto.ProductUpdateDto;
import taewan.Smart.product.entity.Product;
import taewan.Smart.product.repository.ProductRepository;
import taewan.Smart.product.service.ProductServiceImpl;
import taewan.Smart.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock private ProductRepository productRepository;
    @InjectMocks private ProductServiceImpl productService;
    private static String root = "/Users/taewan/Desktop/";

    private static Map<String, String> codeInfo = Map.of(
            "A01", "후드 티셔츠",
            "A02", "맨투맨",
            "B01", "코트",
            "B02", "패딩",
            "C01", "트레이닝 팬츠",
            "C02", "숏팬츠"
    );

    static List<ProductSaveDto> dtos = new ArrayList<>();

//    static List<ProductUpdateDto> udtos = new ArrayList<>();

    @BeforeAll
    static void setup() throws IOException {
        String[] tmp = {"A", "B", "C"};
        String[] tmp2 = {"M", "W"};

        for (int i = 1; i <= 3; i++) {
            ProductSaveDto dto = new ProductSaveDto();
            dto.setName("product" + i);
            dto.setPrice((int)(Math.random() * 10 + 1) * 10000);
            dto.setCode(tmp[i - 1] + "0" + (i % 2 + 1) + tmp2[i % 2]);
            dto.setSize("s,m,l,xl,xxl");
            dto.setDetailInfo(createMultipartFile(dto.getCode(), i));
            List<MultipartFile> imgs = new ArrayList<>();
            for (int j = 1; j <= 3; j++)
                imgs.add(createMultipartFile(dto.getCode(), j));
            dto.setImgFiles(imgs);
            dtos.add(dto);

//            ProductUpdateDto dto2 = new ProductUpdateDto();
//            dto2.setId((long)i);
//            dto2.setName(dto.getName());
//            dto2.setPrice(dto.getPrice());
//            dto2.setCode(dto.getCode());
//            dto2.setSize(dto.getSize());
//            udtos.add(dto2);
        }
    }

//    @AfterEach
//    void clean() {
//        FileUtils.deleteDirectory(root + "images");
//    }

    static MultipartFile createMultipartFile(String code, int i) throws IOException {
        String keyword = codeInfo.get(code.substring(0, 3)) + i;
        String path = root + "testImg/product";
        File[] imgs = new File(path).listFiles();
        String imgName = "구두1.jpeg";
        String contentType = "image/";

        for (File img : imgs) {
            String normalizedName = Normalizer.normalize(img.getName(), Normalizer.Form.NFC);
            if (normalizedName.contains(keyword)) {
                imgName = normalizedName;
                contentType += imgName.substring(imgName.lastIndexOf(".") + 1);
                break;
            }
        }
        if (contentType.equals("image/"))
            contentType += "jpeg";
        return new MockMultipartFile("image", imgName, contentType, new FileInputStream(path + "/" + imgName));
    }

    @Test
    void 제품_저장() {
        //given
        ProductSaveDto dto = dtos.get(0);
        Product product = new Product(dto, "", "");
        when(productRepository.save(any())).thenReturn(product);

        //when
        productService.save(dto);
//        String dirPath = root + "images/products/" + dto.getCode() + "/" + dto.getName();
//        int productImgCount = new File(dirPath + "/view").list().length;
//        int detailImgAndDir = new File(dirPath).list().length;
//
//        //then
//        assertThat(productImgCount).isEqualTo(dto.getImgFiles().size());
//        assertThat(detailImgAndDir).isEqualTo(2);
//        verify(productRepository, times(1)).save(any());
    }

//    @Test
//    void 제품_단일조회() {
//        //given
//        Product product = entities.get(0);
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//
//        //when
//        ProductInfoDto found = productService.findOne(1L);
//
//        //then
//        assertThat(found.toString().replace("ProductInfoDto", ""))
//                .isEqualTo(product.toString().replace("Product", ""));
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void 없는_제품_단일조회() {
//        //given
//        Product product = entities.get(0);
//        when(productRepository.findById(1L)).thenReturn(Optional.empty());
//
//        //when //then
//        assertThrows(NoSuchElementException.class, () -> productService.findOne(1L));
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void 제품_전체조회() {
//        //given
//        when(productRepository.findAll()).thenReturn(entities);
//
//        //when
//        List<ProductInfoDto> found = productService.findAll();
//
//        //then
//        assertThat(found.size()).isEqualTo(3);
//        for (int i = 0; i < 3; i++) {
//            assertThat(found.get(i).toString().replace("ProductInfoDto", ""))
//                    .isEqualTo(entities.get(i).toString().replace("Product", ""));
//        }
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    void 제품_수정() {
//        //given
//        Product product = entities.get(0);
//        ProductUpdateDto dto = new ProductUpdateDto();
//        dto.setProductId(1L);
//        dto.setProductName("product5");
//        dto.setProductImg("productImg5");
//        dto.setPrice(1000 * 5);
//        dto.setCategory(10000L + 5);
//        dto.setProductInformation("productInfo5");
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//
//        //when
//        Long id = productService.modify(dto);
//
//        //then
//        assertThat(id).isEqualTo(1L);
//        assertThat(product.toString().replace("Product", ""))
//                .isEqualTo(dto.toString().replace("ProductUpdateDto", ""));
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void 제품_삭제() {
//        //given
//
//        //when
//        productService.delete(1L);
//
//        //then
//        verify(productRepository, times(1)).deleteById(1L);
//    }
}