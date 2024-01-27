package project.server.mvc.servlet.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cookies {

    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String DELIMITER = "=";
    private static final String COOKIE_DELIMITER = ", ";
    private static final String CARRIAGE_RETURN = "\r\n";

    private final List<String> cookies;
    private final Map<String, String> cookiesMap = new HashMap<>();
    public static final Cookies emptyCookies = new Cookies();

    public Cookies() {
        this.cookies = new ArrayList<>();
    }

    public Cookies(List<String> cookieValues) {
        this.cookies = cookieValues;
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

    public String getValue(String name) {
        return this.cookiesMap.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", String.join(COOKIE_DELIMITER, cookies)) + CARRIAGE_RETURN;
    }
}
