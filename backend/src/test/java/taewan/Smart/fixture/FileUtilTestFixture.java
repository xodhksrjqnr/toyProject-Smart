package taewan.Smart.fixture;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import taewan.Smart.global.utils.PropertyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileUtilTestFixture {
    public static String ROOT;
    public static String PRODUCT_IMG_PATH;
    public static String DETAIL_INFO_IMG_PATH;
    public static String SERVER_ADDRESS;
    public static String IMG_FOLDER_PATH;
    public static String ACCESS_IMG_URL;

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream resourceStream = loader.getResourceAsStream("application-test.properties");

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
}
