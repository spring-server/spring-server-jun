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
    private final Map<String, Cookie> cookiesMap;
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
            String[] cookiePairs = cookie.split(COOKIE_DELIMITER);
            for (String pair : cookiePairs) {
                String[] keyValue = pair.trim().split(DELIMITER, 2);
                if (keyValue.length == 2) {
                    String key = keyValue[KEY];
                    String value = keyValue[VALUE];
                    cookiesMap.put(key, new Cookie(key, value));
                }
            }
        }
    }

    public boolean isEmpty() {
        return this.cookies.isEmpty();
    }

    public Map<String, Cookie> getCookiesMap() {
        return cookiesMap;
    }

    public Cookie getValue(String name) {
        return this.cookiesMap.get(name);
    }

    public void add(Cookie cookie) {
        this.cookiesMap.put(cookie.name(), cookie);
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
                .append(cookiesMap.get(key).value());
        }
        return stringBuilder.toString().trim() + CARRIAGE_RETURN;
    }
}
