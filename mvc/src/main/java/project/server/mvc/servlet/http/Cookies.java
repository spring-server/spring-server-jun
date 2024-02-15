package project.server.mvc.servlet.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookies {

    private static final String EMPTY_STRING = "";
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String DELIMITER = "=";
    private static final String COOKIE_DELIMITER = "; ";
    private static final String SET_COOKIE = "Set-Cookie";

    private final Map<String, Cookie> cookiesMap;
    public static final Cookies emptyCookies = new Cookies();

    public Cookies() {
        this.cookiesMap = new HashMap<>();
    }

    public Cookies(List<String> cookieValues) {
        this.cookiesMap = new HashMap<>();
        initCookieMap(cookieValues);
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
        return this.cookiesMap.isEmpty();
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
        if (cookiesMap.isEmpty()) {
            return EMPTY_STRING;
        }

        String cookie = cookiesMap.get(SET_COOKIE).value();
        if (cookie.trim().isBlank()) {
            return EMPTY_STRING;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : cookiesMap.keySet()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(COOKIE_DELIMITER);
            }
            stringBuilder.append(key)
                .append(DELIMITER)
                .append(cookiesMap.get(key).value());
        }
        return stringBuilder.toString().trim();
    }
}
