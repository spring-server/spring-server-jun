package project.server.app.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.fasterxml.jackson.databind.PropertyNamingStrategies.KEBAB_CASE;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.InputStream;
import project.server.jdbc.core.ConfigMap;

public class ConfigMapLoader {

    public ConfigMap getConfigMap() throws IOException {
        return loadConfig("application.yml");
    }

    public ConfigMap loadConfig(String resourcePath) throws IOException {
        InputStream inputStream = getInputStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.setPropertyNamingStrategy(KEBAB_CASE);
        return mapper.readValue(inputStream, ConfigMap.class);
    }

    private InputStream getInputStream(String resourcePath) {
        return ConfigMapLoader.class.getClassLoader()
            .getResourceAsStream(resourcePath);
    }
}
