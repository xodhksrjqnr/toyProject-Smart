package taewan.Smart.global.utils;

import org.springframework.core.env.Environment;
import taewan.Smart.global.config.ApplicationContextProvider;

public class PropertyUtil {

    public static final String SERVER_ADDRESS;
    public static final String CLIENT_ADDRESS;
    public static final String ROOT_PATH;
    public static final String ACCESS_PATH;
    public static final String RESOURCE_PATH;
    public static final String SECRET_KEY;

    static {
        Environment e = ApplicationContextProvider.getApplicationContext().getEnvironment();
        SERVER_ADDRESS = e.getProperty("address.server");
        CLIENT_ADDRESS = e.getProperty("address.client");
        ROOT_PATH = e.getProperty("path.home");
        ACCESS_PATH = e.getProperty("path.access");
        RESOURCE_PATH = e.getProperty("path.resource");
        SECRET_KEY = e.getProperty("auth.secretKey");
    }
}
