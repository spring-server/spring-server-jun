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

    private final Map<String, Object> attributes;

    public RequestBody(String body) {
        if (body == null || body.isBlank()) {
            attributes = new HashMap<>();
            return;
        }
        this.attributes = parseBody(body);
    }

    public RequestBody(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public static RequestBody createJsonRequestBody(String body) {
        if (body == null || body.isBlank()) {
            return new RequestBody(new HashMap<>());
        }

        String parsedBody = body.replaceAll("\"", "")
            .replaceAll("\\{", "")
            .replaceAll("}", "");

        Map<String, Object> attribute = new HashMap<>();

        String[] bodyArray = parsedBody.split(",");
        for (String eachBody : bodyArray) {
            String[] element = eachBody.split(":");
            attribute.put(element[KEY], element[VALUE]);
        }
        return new RequestBody(attribute);
    }

    private Map<String, Object> parseBody(String body) {
        Map<String, Object> attributes = new HashMap<>();
        String[] bodyArray = body.split(VALUES_DELIMITER);
        for (String element : bodyArray) {
            String[] pair = element.split(VALUE_DELIMITER);
            attributes.put(pair[KEY], pair[VALUE]);
        }
        return attributes;
    }

    public void setAttribute(
        String key,
        Object value
    ) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public String toString() {
        return String.format("%s", attributes == null ? EMPTY_STRING + CARRIAGE_RETURN : attributes + CARRIAGE_RETURN);
    }
}
