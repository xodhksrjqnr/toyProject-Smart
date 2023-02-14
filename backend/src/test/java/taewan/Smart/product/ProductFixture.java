package taewan.Smart.product;

import taewan.Smart.domain.product.entity.Product;

import java.util.*;

public class ProductFixture {

    private static String[] category = {"A", "B", "C", "D"};
    private static String[] categoryItem = {"01", "02"};
    private static String[] gender = {"M", "W"};

    private static String createPath(String name, String code) {
        return code + "/" + name;
    }

    public static String createImgFilePath(String root, String name, String code) {
        return root + createPath(name, code) + "/view";
    }

    public static String createDetailInfoPath(String root, String name, String code) {
        return root + createPath(name, code) + "/" + UUID.randomUUID();
    }

    public static Product createProduct(String root) {
        return createProduct(root, 0);
    }

    public static Product createProduct(String root, int index) {
        String name = "product" + index;
        String code = category[index % 4] + categoryItem[index % 2] + gender[index % 2];

        return Product.builder()
                .name(name)
                .price((int)(Math.random() * 10 + 1) * 10000)
                .code(code)
                .size("s,m,l,xl,xxl")
                .imgFolderPath(createImgFilePath(root, name, code))
                .detailInfo(createDetailInfoPath(root, name, code))
                .build();
    }

    public static List<Product> createProducts(String root) {
        return createProducts(root, 10);
    }

    public static List<Product> createProducts(String root, int size) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            products.add(createProduct(root, i + 1));
        }
        return products;
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
}
