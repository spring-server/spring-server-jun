package project.server.mvc.servlet.http;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String EMPTY_STRING = "";
    private static final String VALUE_DELIMITER = "=";
    private static final String VALUES_DELIMITER = "&";
    private static final int KEY = 0;
    private static final int VALUE = 1;

    private final Map<String, String> attributes;

    public RequestBody(String body) {
        if (body == null || body.isBlank()) {
            attributes = null;
            return;
        }
        this.attributes = parseBody(body);
    }

    private Map<String, String> parseBody(String body) {
        Map<String, String> attributes = new HashMap<>();
        String[] bodyArray = body.split(VALUES_DELIMITER);
        for (String element : bodyArray) {
            String[] pair = element.split(VALUE_DELIMITER);
            attributes.put(pair[KEY], pair[VALUE]);
        }
        return attributes;
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public String toString() {
        return String.format("%s", attributes == null ? EMPTY_STRING + CARRIAGE_RETURN : attributes + CARRIAGE_RETURN);
    }
}
