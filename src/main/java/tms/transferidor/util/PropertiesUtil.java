package tms.transferidor.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties carregar() throws IOException {
        Properties props = new Properties();

        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            props.load(input);
        }

        return props;
    }
}
