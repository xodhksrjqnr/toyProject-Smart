package taewan.Smart.global.util;

import org.springframework.core.env.Environment;
import taewan.Smart.global.config.ApplicationContextProvider;

public class PropertyUtils {

    private static String serverAddress;
    private static String clientAddress;
    private static String rootPath;
    private static String accessPath;
    private static String resourcePath;
    private static String secretKey;
    private static String imgFolderPath;
    private static String accessImgUrl;
    private static String emptyImgPath;

    static {
        Environment e = ApplicationContextProvider.getApplicationContext().getEnvironment();
        serverAddress = e.getProperty("address.server");
        clientAddress = e.getProperty("address.client");
        rootPath = e.getProperty("path.home");
        accessPath = e.getProperty("path.access");
        resourcePath = e.getProperty("path.resource");
        secretKey = e.getProperty("auth.secretKey");
        imgFolderPath = rootPath + e.getProperty("path.image");
        accessImgUrl = serverAddress + e.getProperty("path.image");
        emptyImgPath = e.getProperty("path.image.empty");
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static String getClientAddress() {
        return clientAddress;
    }

    public static String getRootPath() {
        return rootPath;
    }

    public static String getAccessPath() {
        return accessPath;
    }

    public static String getResourcePath() {
        return resourcePath;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static String getImgFolderPath() {
        return imgFolderPath;
    }

    public static String getAccessImgUrl() {
        return accessImgUrl;
    }

    public static String getEmptyImgPath() {
        return emptyImgPath;
    }
}
