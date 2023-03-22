package taewan.Smart.fixture;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.entity.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

public class ProductTestFixture {
    // Product
    public static Product createProduct(int index) {
        return Product.builder()
                .name("test" + index)
                .price(1000 * index)
                .code("A01M")
                .size("s,m,l")
                .imgPath("testPath" + index)
                .build();
    }

    public static Product createProduct() {
        return createProduct(1);
    }

    public static String toString(Product product) {
        return product.getProductId() + "," +
                product.getName() + "," +
                product.getCode() + "," +
                product.getPrice() + "," +
                product.getSize() + "," +
                product.getImgPath();
    }

    // ProductSaveDto
    public static List<MultipartFile> getImgFiles(int size, String imgPath) {
        List<MultipartFile> list = new ArrayList<>();

        try {
            File[] files = new File(imgPath).listFiles();

            for (File f : files) {
                if (size-- == 0) break;
                String name = f.getName();
                list.add(new MockMultipartFile(
                        "MultipartFile",
                        name,
                        "/" + name.substring(name.lastIndexOf('.') + 1),
                        new FileInputStream(f)));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return list;
    }

    public static ProductSaveDto createProductSaveDto(String imgPath) {
        return createProductSaveDto(1, imgPath);
    }

    public static ProductSaveDto createProductSaveDto(int index, String imgPath) {
        return ProductSaveDto.builder()
                .imgFiles(getImgFiles(3, imgPath))
                .name("product" + index)
                .price(1000 * index)
                .code("A01M")
                .size("s,m,l,xl,xxl")
                .detailInfo(getImgFiles(1, imgPath).get(0))
                .build();
    }

    public static List<ProductSaveDto> createProductSaveDtoList(int size, String imgPath) {
        List<ProductSaveDto> list = new ArrayList<>();

        for (int i = 0; i < size; i++)
            list.add(createProductSaveDto(i + 1, imgPath));
        return list;
    }

    //ProductInfoDto
    public static String toString(ProductInfoDto dto) {
        String str = dto.getProductId() + "," +
                dto.getName() + "," +
                dto.getCode() + "," +
                dto.getPrice() + "," +
                dto.getSize() + "," +
                dto.getDetailInfo() + ",";

        for (int i = 0; i < dto.getImgFiles().size(); i++) {
            str += dto.getImgFiles().get(i).toString() + ",";
        }
        return str;
    }

    //ProductController
    private static Map<String, Object> createDefault = new HashMap<>();

    public static void setCreateDefault() {
        MultipartFile file1 = new MockMultipartFile("imgFiles", new byte[0]);
        MultipartFile file2 = new MockMultipartFile("detailInfo", new byte[0]);

        createDefault.put("productId", 1L);
        createDefault.put("imgFiles", file1);
        createDefault.put("name", "test");
        createDefault.put("price", 1000);
        createDefault.put("code", "A01M");
        createDefault.put("size", "s,m,l,xl");
        createDefault.put("detailInfo", file2);
    }

    private static MockHttpServletRequestBuilder setParam(MockMultipartHttpServletRequestBuilder sr) {
        return sr.file((MockMultipartFile) createDefault.get("imgFiles"))
                .file((MockMultipartFile) createDefault.get("detailInfo"))
                .param("name", (String) createDefault.get("name"))
                .param("price", String.valueOf(createDefault.get("price")))
                .param("code", (String) createDefault.get("code"))
                .param("size", (String) createDefault.get("size"));
    }

    public static MockHttpServletRequestBuilder createUploadRequest() {
        return setParam(multipart("/products"));

    }

    public static MockHttpServletRequestBuilder createUploadRequest(String target, Object value) {
        Object preValue = createDefault.get(target);
        createDefault.put(target, value);
        MockHttpServletRequestBuilder sr = createUploadRequest();
        createDefault.put(target, preValue);
        return sr;
    }

    public static MockHttpServletRequestBuilder createUpdateRequest() {
        return setParam(multipart("/products/update"))
                .param("productId", String.valueOf(createDefault.get("productId")));
    }

    public static MockHttpServletRequestBuilder createUpdateRequest(String target, Object value) {
        Object preValue = createDefault.get(target);
        createDefault.put(target, value);
        MockHttpServletRequestBuilder sr = createUpdateRequest();
        createDefault.put(target, preValue);
        return sr;
    }
}
