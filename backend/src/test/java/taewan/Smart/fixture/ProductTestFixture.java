package taewan.Smart.fixture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.domain.product.dto.ProductSaveDto;
import taewan.Smart.domain.product.dto.ProductUpdateDto;
import taewan.Smart.domain.product.entity.Product;
import taewan.Smart.global.utils.PropertyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static taewan.Smart.global.error.ExceptionStatus.PRODUCT_IMAGE_EMPTY;
import static taewan.Smart.global.utils.FileUtil.saveFile;
import static taewan.Smart.global.utils.FileUtil.saveFiles;

public class ProductTestFixture {
    private static String[] category = {"A", "B", "C", "D"};
    private static String[] categoryItem = {"01", "02"};
    private static String[] gender = {"M", "W"};

    public static String ROOT;
    public static String PRODUCT_IMG_PATH;
    public static String DETAIL_INFO_IMG_PATH;
    public static String SERVER_ADDRESS;
    public static String IMG_FOLDER_PATH;
    public static String ACCESS_IMG_URL;

    private static List<Product> products;

    private static List<ProductSaveDto> productSaveDtoList;
    private static Map<String, Integer> createdCodeInfo;

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream resourceStream = loader.getResourceAsStream("application.properties");

        try {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ROOT = properties.getProperty("path.home");
        SERVER_ADDRESS = properties.getProperty("address.server");
        PRODUCT_IMG_PATH = properties.getProperty("path.testImg.product");
        DETAIL_INFO_IMG_PATH = properties.getProperty("path.testImg.detail");
        IMG_FOLDER_PATH = ROOT + properties.getProperty("path.image");
        ACCESS_IMG_URL = SERVER_ADDRESS + properties.getProperty("path.image");
        ReflectionTestUtils.setField(PropertyUtil.class, "ROOT_PATH", ROOT);
        ReflectionTestUtils.setField(PropertyUtil.class, "SERVER_ADDRESS", SERVER_ADDRESS);
        ReflectionTestUtils.setField(PropertyUtil.class, "IMG_FOLDER_PATH", IMG_FOLDER_PATH);
        ReflectionTestUtils.setField(PropertyUtil.class, "ACCESS_IMG_URL", ACCESS_IMG_URL);
        products = createProducts();
        productSaveDtoList = createProductSaveDtoList();
        createdCodeInfo = getCodeInfo(products);
    }

    public static List<MultipartFile> getImgFiles(int size) {
        List<MultipartFile> list = new ArrayList<>();

        try {
            File[] files = new File(PRODUCT_IMG_PATH).listFiles();

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

    public static MultipartFile getImgFile() {
        try {
            File file = new File(DETAIL_INFO_IMG_PATH).listFiles()[0];
            String name = file.getName();

            return new MockMultipartFile(
                    "MultipartFile",
                    name,
                    "/" + name.substring(name.lastIndexOf('.') + 1),
                    new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static Integer createPrice() {
        return (int)(Math.random() * 10 + 1) * 10000;
    }

    private static String createCode(int index) {
        return category[index % 4] + categoryItem[index % 2] + gender[index % 2];
    }

    private static String createPath(String name, String code) {
        return code + "/" + name;
    }

    public static String createImgFilePath(String root, String name, String code) {
        return root + createPath(name, code) + "/view";
    }

    public static String createDetailInfoPath(String root, String name, String code) {
        return root + createPath(name, code) + "/" + UUID.randomUUID();
    }

    public static Map<String, Integer> getCodeInfo(List<Product> products) {
        Map<String, Integer> info = new HashMap<>();
        int[][] pattern = {{0, 1}, {0, 3}, {0, 4}, {1, 3}, {1, 4}, {3, 4}};

        for (String c1 : category) {
            info.put(c1, 0);
            for (String c2 : categoryItem) {
                info.put(c2, 0);
                info.put(c1 + c2, 0);
                for (String c3 : gender) {
                    info.put(c3, 0);
                    info.put(c2 + c3, 0);
                    info.put(c1 + c2 + c3, 0);
                }
            }
        }
        products.forEach(p -> {
            String code = p.getCode();
            for (int[] t : pattern) {
                String cur = code.substring(t[0], t[1]);
                info.put(cur, info.get(cur) + 1);
            }
        });
        return info;
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static List<ProductSaveDto> getProductSaveDtoList() {
        return productSaveDtoList;
    }
    public static Map<String, Integer> getDefaultCodeInfo() {
        return createdCodeInfo;
    }

    public static String saveImgFile(ProductSaveDto dto) {
        try {
            saveFiles(dto.getImgFiles(), dto.getViewPath());
            return saveFile(dto.getDetailInfo(), dto.getDirectoryPath());
        } catch (NullPointerException e) {
            throw PRODUCT_IMAGE_EMPTY.exception();
        }
    }

    public static Product createProduct() {
        return createProduct(1);
    }

    public static Product createProduct(int index) {
        String name = "product" + index;
        String code = createCode(index);

        return Product.builder()
                .name(name)
                .price(createPrice())
                .code(code)
                .size("s,m,l,xl,xxl")
                .imgFolderPath(createImgFilePath(ROOT, name, code))
                .detailInfo(createDetailInfoPath(ROOT, name, code))
                .build();
    }

    public static List<Product> createProducts() {
        return createProducts(10);
    }

    public static List<Product> createProducts(int size) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            products.add(createProduct(i + 1));
        }
        return products;
    }

    public static ProductSaveDto createProductSaveDto() {
        return createProductSaveDto(1);
    }

    public static ProductSaveDto createProductSaveDto(int index) {
        return ProductSaveDto.builder()
                .imgFiles(getImgFiles(3))
                .name("product" + index)
                .price(createPrice())
                .code(createCode(index))
                .size("s,m,l,xl,xxl")
                .detailInfo(getImgFile())
                .build();
    }

    public static List<ProductSaveDto> createProductSaveDtoList() {
        return createProductSaveDtoList(10);
    }

    public static List<ProductSaveDto> createProductSaveDtoList(int size) {
        List<ProductSaveDto> list = new ArrayList<>();

        for (int i = 0; i < size; i++)
            list.add(createProductSaveDto(i + 1));
        return list;
    }

    public static ProductUpdateDto createProductUpdateDto() {
        return createProductUpdateDto(1);
    }

    public static ProductUpdateDto createProductUpdateDto(int index) {
        return ProductUpdateDto.builder()
                .productId((long)index)
                .imgFiles(getImgFiles(3))
                .name("updated-product" + index)
                .price(createPrice() + 10000)
                .code(createCode(index))
                .size("s,m,l,xl,xxl")
                .detailInfo(getImgFile())
                .build();
    }

    public static Page<Product> toPage(List<Product> products, Pageable pageable, long total) {
        return new PageImpl<>(products, pageable, total);
    }
}
