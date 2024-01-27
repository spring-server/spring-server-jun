package project.server.mvc.springframework.ui;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap extends LinkedHashMap<String, Object> {

    public ModelMap addAttribute(
        String attributeName,
        Object attributeValue
    ) {
        put(attributeName, attributeValue);
        return this;
    }

    public ModelMap addAllAttributes(Map<String, ?> attributes) {
        if (attributes != null) {
            putAll(attributes);
        }
        return this;
    }
}
