package taewan.Smart.fixture;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomFileUtilsTestFixture {
    public static final String IMG_FOLDER_PATH;
    private static final String PRODUCT_IMG_PATH;

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream resourceStream = loader.getResourceAsStream("application.properties");

        try {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IMG_FOLDER_PATH = properties.getProperty("path.home")
                + properties.getProperty("path.image")
                + "products";
        PRODUCT_IMG_PATH = properties.getProperty("path.testImg");
    }

    public static List<MultipartFile> getImgFiles(int size) {
        List<MultipartFile> list = new ArrayList<>(size);

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
}
