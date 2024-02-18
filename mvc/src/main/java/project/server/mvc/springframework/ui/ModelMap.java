package project.server.mvc.springframework.ui;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelMap {

    private final Map<String, Object> map = new LinkedHashMap();

    public void put(
        String attributeName,
        Object attributeValue
    ) {
        map.put(attributeName, attributeValue);
    }

    public Object getAttribute(String key) {
        return map.get(key);
    }

    public String[] keys() {
        return new String[map.keySet().size()];
    }
}
