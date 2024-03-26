package project.server.mvc.servlet.http;

import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.emptyList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import static project.server.mvc.servlet.http.ContentType.APPLICATION_JSON;

public class HttpHeaders {

    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final String HEADER_DELIMITER = ": ";
    private static final String HEADER_JOINING_DELIMITER = ", ";
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

    public String getHeaderValue(String key) {
        return headers.get(key).stream()
            .filter(headerKey -> headerKey.getName().equals(key))
            .findAny()
            .map(HttpHeader::getValue)
            .orElseGet(() -> null);
    }

    public int getContentLength() {
        List<HttpHeader> headers = this.headers.getOrDefault(
            CONTENT_LENGTH, List.of(new HttpHeader(CONTENT_LENGTH, "0"))
        );
        return Integer.parseInt(headers.get(0).getValue());
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    public Cookies getCookies() {
        return cookies;
    }

    public void addHeader(
        String key,
        String value
    ) {
        List<HttpHeader> values = this.headers
            .getOrDefault(key, new ArrayList<>());
        values.add(new HttpHeader(key, value));
        this.headers.put(key, values);
    }

    @Override
    public String toString() {
        List<String> keys = new ArrayList<>(this.headers.keySet());
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys) {
            stringBuilder.append(key)
                .append(HEADER_DELIMITER)
                .append(joiningValues(headers.get(key)));
        }

        stringBuilder.append(cookies)
            .append(CARRIAGE_RETURN);
        return stringBuilder.toString();
    }

    private String joiningValues(List<HttpHeader> headers) {
        return headers.stream()
            .map(HttpHeader::getValue)
            .collect(joining(HEADER_JOINING_DELIMITER)) + CARRIAGE_RETURN;
    }

    public boolean isContentType(ContentType contentType) {
        List<HttpHeader> contentTypeHeader = headers.get(contentType.getValue());
        if (contentTypeHeader == null || contentTypeHeader.isEmpty()) {
            return false;
        }
        Optional<HttpHeader> findApplicationJson = contentTypeHeader.stream()
            .filter(header -> header.getValue().equals(APPLICATION_JSON.getValue()))
            .findAny();
        return findApplicationJson.isPresent();
    }
}
