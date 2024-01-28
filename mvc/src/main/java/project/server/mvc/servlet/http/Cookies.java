package project.server.mvc.servlet.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookies {

    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String CARRIAGE_RETURN = "\r\n";

    private final List<String> cookies;
    private final Map<String, String> cookiesMap;
    public static final Cookies emptyCookies = new Cookies();

    public Cookies() {
        this.cookies = new ArrayList<>();
        this.cookiesMap = new HashMap<>();
    }

    public Cookies(List<String> cookieValues) {
        this.cookies = cookieValues;
        this.cookiesMap = new HashMap<>();
        initCookieMap(cookies);
    }

    private void initCookieMap(List<String> cookies) {
        for (String cookie : cookies) {
            String[] pair = cookie.split(DELIMITER);
            cookiesMap.put(pair[KEY], pair[VALUE]);
        }
    }

    public boolean isEmpty() {
        return this.cookies.isEmpty();
    }

    public Map<String, String> getCookiesMap() {
        return cookiesMap;
    }

    public String getValue(String name) {
        return this.cookiesMap.get(name);
    }

    public void add(Cookie cookie) {
        this.cookiesMap.put(cookie.name(), cookie.value());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : cookiesMap.keySet()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(COOKIE_DELIMITER);
            }
            stringBuilder.append(key)
                .append(DELIMITER)
                .append(cookiesMap.get(key));
        }
        return stringBuilder.toString().trim() + CARRIAGE_RETURN;
    }
}
