package project.server.mvc.servlet.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.emptyList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeaders {

    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String HEADER_DELIMITER = ": ";
    private static final String MULTI_VALUE_DELIMITER = ",";
    private static final String CARRIAGE_RETURN = "\r\n";
    private static final String COOKIE = "Cookie";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, List<HttpHeader>> headers;
    private final Cookies cookies;

    public HttpHeaders() {
        this.headers = new HashMap<>();
        this.cookies = new Cookies();
    }

    public HttpHeaders(List<String> headers) {
        this.headers = new HashMap<>();
        initHeaders(headers);
        this.cookies = initCookie();
    }

    private void initHeaders(List<String> headers) {
        for (String header : headers) {
            String[] pair = header.split(HEADER_DELIMITER);
            String[] multiValues = pair[VALUE].split(MULTI_VALUE_DELIMITER);
            addHeader(pair, multiValues);
        }
    }

    private void addHeader(
        String[] pair,
        String[] multiValues
    ) {
        Arrays.stream(multiValues)
            .forEach(value -> addHeader(pair, value.trim()));
    }

    private void addHeader(
        String[] pair,
        String value
    ) {
        String key = pair[KEY];
        List<HttpHeader> values = this.headers
            .getOrDefault(key, new ArrayList<>());
        values.add(new HttpHeader(pair[KEY], value));
        this.headers.put(key, values);
    }

    private Cookies initCookie() {
        List<HttpHeader> cookies = this.headers.getOrDefault(COOKIE, emptyList());
        if (cookies.isEmpty()) {
            return Cookies.emptyCookies;
        }
        List<String> cookieValues = cookies.stream()
            .map(HttpHeader::getValue)
            .toList();
        return new Cookies(cookieValues);
    }

    public List<HttpHeader> getValue(String key) {
        return headers.get(key);
    }

    public int getContentLength() {
        List<HttpHeader> headers = this.headers.getOrDefault(
            CONTENT_LENGTH, List.of(new HttpHeader(CONTENT_LENGTH, "0"))
        );
        return Integer.parseInt(headers.get(0).getValue());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        List<String> keys = new ArrayList<>(this.headers.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            if (COOKIE.equals(key)) {
                stringBuilder.append(key)
                    .append(HEADER_DELIMITER)
                    .append(cookies);
                continue;
            }
            stringBuilder.append(key)
                .append(HEADER_DELIMITER)
                .append(joiningValues(headers.get(key)));
        }
        return stringBuilder.toString();
    }

    private String joiningValues(List<HttpHeader> headers) {
        return headers.stream()
            .map(HttpHeader::getValue)
            .collect(Collectors.joining(", ")) + CARRIAGE_RETURN;
    }
}
