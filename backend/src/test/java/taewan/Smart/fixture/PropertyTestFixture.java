package taewan.Smart.fixture;

import org.springframework.test.util.ReflectionTestUtils;
import taewan.Smart.global.util.PropertyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyTestFixture {

    public static String SERVER_ADDRESS;
    public static String CLIENT_ADDRESS;
    public static String ROOT_PATH;
    public static String RESOURCE_PATH;
    public static String ACCESS_PATH;
    public static String IMG_FOLDER_PATH;
    public static String EMPTY_IMG_PATH;
    public static String PRODUCT_TEST_IMG_PATH;
    public static String SECRET_KEY;
    public static String ACCESS_IMG_URL;

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream resourceStream = loader.getResourceAsStream("application.properties");

        try {
            properties.load(resourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SERVER_ADDRESS = properties.getProperty("address.server");
        CLIENT_ADDRESS = properties.getProperty("address.client");
        ROOT_PATH = properties.getProperty("path.home");
        RESOURCE_PATH = properties.getProperty("path.resource");
        ACCESS_PATH = properties.getProperty("path.access");
        IMG_FOLDER_PATH = ROOT_PATH + properties.getProperty("path.image");
        EMPTY_IMG_PATH = properties.getProperty("path.image.empty");
        PRODUCT_TEST_IMG_PATH = properties.getProperty("path.testImg.product");
        SECRET_KEY = properties.getProperty("jwt.secret.key");
        ACCESS_IMG_URL = SERVER_ADDRESS + properties.getProperty("path.image");

        ReflectionTestUtils.setField(PropertyUtils.class, "serverAddress", SERVER_ADDRESS);
        ReflectionTestUtils.setField(PropertyUtils.class, "clientAddress", CLIENT_ADDRESS);
        ReflectionTestUtils.setField(PropertyUtils.class, "rootPath", ROOT_PATH);
        ReflectionTestUtils.setField(PropertyUtils.class, "accessPath", ACCESS_PATH);
        ReflectionTestUtils.setField(PropertyUtils.class, "resourcePath", RESOURCE_PATH);
        ReflectionTestUtils.setField(PropertyUtils.class, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(PropertyUtils.class, "imgFolderPath", IMG_FOLDER_PATH);
        ReflectionTestUtils.setField(PropertyUtils.class, "accessImgUrl", ACCESS_IMG_URL);
        ReflectionTestUtils.setField(PropertyUtils.class, "emptyImgPath", EMPTY_IMG_PATH);
    }
}
