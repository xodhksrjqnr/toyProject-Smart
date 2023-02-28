package taewan.Smart.fixture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.domain.product.dto.ProductInfoDto;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.entity.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static taewan.Smart.global.error.ExceptionStatus.PRODUCT_IMAGE_EMPTY;
import static taewan.Smart.global.util.CustomFileUtils.saveFile;
import static taewan.Smart.global.util.CustomFileUtils.saveFiles;

public class ProductTestFixture {
//    private static String[] category = {"A", "B", "C", "D"};
//    private static String[] categoryItem = {"01", "02"};
//    private static String[] gender = {"M", "W"};
//    private static List<Product> products;
//    private static List<ProductSaveDto> productSaveDtoList;
//    private static Map<String, Integer> createdCodeInfo;
//
//    static {
//        products = createProducts();
//        productSaveDtoList = createProductSaveDtoList();
//        createdCodeInfo = getCodeInfo(products);
//    }

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

    public static String toStringForTest(Product product) {
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
    public static String toStringForTest(ProductInfoDto dto) {
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
//
//    public static MultipartFile getImgFile() {
//        try {
//            File file = new File(PropertyTestFixture.PRODUCT_TEST_IMG_PATH).listFiles()[0];
//            String name = file.getName();
//
//            return new MockMultipartFile(
//                    "MultipartFile",
//                    name,
//                    "/" + name.substring(name.lastIndexOf('.') + 1),
//                    new FileInputStream(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//    }
//
//
//
//    private static String createImgSavePath(String root, String name, String code) {
//        return root + createPath(name, code);
//    }
//
//    private static String createPath(String name, String code) {
//        return code + "/" + name;
//    }
//
//    public static Map<String, Integer> getCodeInfo(List<Product> products) {
//        Map<String, Integer> info = new HashMap<>();
//        int[][] pattern = {{0, 1}, {0, 3}, {0, 4}, {1, 3}, {1, 4}, {3, 4}};
//
//        for (String c1 : category) {
//            info.put(c1, 0);
//            for (String c2 : categoryItem) {
//                info.put(c2, 0);
//                info.put(c1 + c2, 0);
//                for (String c3 : gender) {
//                    info.put(c3, 0);
//                    info.put(c2 + c3, 0);
//                    info.put(c1 + c2 + c3, 0);
//                }
//            }
//        }
//        products.forEach(p -> {
//            String code = p.getCode();
//            for (int[] t : pattern) {
//                String cur = code.substring(t[0], t[1]);
//                info.put(cur, info.get(cur) + 1);
//            }
//        });
//        return info;
//    }
//
//    public static List<ProductSaveDto> getProductSaveDtoList() {
//        return productSaveDtoList;
//    }
//    public static Map<String, Integer> getDefaultCodeInfo() {
//        return createdCodeInfo;
//    }
//
//    public static String saveImgFile(ProductSaveDto dto) {
//        try {
//            saveFiles(dto.getImgFiles(), dto.getImgSavePath());
//            return saveFile(dto.getDetailInfo(), dto.getImgSavePath());
//        } catch (NullPointerException e) {
//            throw PRODUCT_IMAGE_EMPTY.exception();
//        }
//    }
//
//
//
//    public static List<ProductSaveDto> createProductSaveDtoList() {
//        return createProductSaveDtoList(10);
//    }
//
//
//
//    public static ProductUpdateDto createProductUpdateDto() {
//        return createProductUpdateDto(1);
//    }
//
//    public static ProductUpdateDto createProductUpdateDto(int index) {
//        return ProductUpdateDto.builder()
//                .productId((long)index)
//                .imgFiles(getImgFiles(3))
//                .name("updated-product" + index)
//                .price(createPrice() + 10000)
//                .code(createCode(index))
//                .size("s,m,l,xl,xxl")
//                .detailInfo(getImgFile())
//                .build();
//    }
//
//    public static Page<Product> toPage(List<Product> products, Pageable pageable, long total) {
//        return new PageImpl<>(products, pageable, total);
//    }
}
