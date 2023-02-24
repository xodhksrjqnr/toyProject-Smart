package taewan.Smart.global.utils;

import org.springframework.core.env.Environment;
import taewan.Smart.global.config.ApplicationContextProvider;

public class PropertyUtil {

    private static String SERVER_ADDRESS;
    private static String CLIENT_ADDRESS;
    private static String ROOT_PATH;
    private static String ACCESS_PATH;
    private static String RESOURCE_PATH;
    private static String SECRET_KEY;
    private static String IMG_FOLDER_PATH;
    private static String ACCESS_IMG_URL;

    static {
        try {
            Environment e = ApplicationContextProvider.getApplicationContext().getEnvironment();
            SERVER_ADDRESS = e.getProperty("address.server");
            CLIENT_ADDRESS = e.getProperty("address.client");
            ROOT_PATH = e.getProperty("path.home");
            ACCESS_PATH = e.getProperty("path.access");
            RESOURCE_PATH = e.getProperty("path.resource");
            SECRET_KEY = e.getProperty("auth.secretKey");
            IMG_FOLDER_PATH = ROOT_PATH + e.getProperty("path.image");
            ACCESS_IMG_URL = SERVER_ADDRESS + e.getProperty("path.image");
        } catch (NullPointerException ex) {
            SERVER_ADDRESS = null;
            CLIENT_ADDRESS = null;
            ROOT_PATH = null;
            ACCESS_PATH = null;
            RESOURCE_PATH = null;
            IMG_FOLDER_PATH = null;
            SECRET_KEY = null;
            ACCESS_IMG_URL = null;
        }
    }

    public static String getServerAddress() {
        return SERVER_ADDRESS;
    }
    public static String getClientAddress() {
        return CLIENT_ADDRESS;
    }
    public static String getRootPath() {
        return ROOT_PATH;
    }
    public static String getAccessPath() {
        return ACCESS_PATH;
    }
    public static String getResourcePath() {
        return RESOURCE_PATH;
    }
    public static String getImgFolderPath() {
        return IMG_FOLDER_PATH;
    }
    public static String getSecretKey() {
        return SECRET_KEY;
    }

    public static String getAccessImgUrl() {
        return ACCESS_IMG_URL;
    }
}
