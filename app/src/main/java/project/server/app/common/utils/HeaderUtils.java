package project.server.app.common.utils;

import java.util.Map;
import project.server.mvc.servlet.http.Cookie;
import project.server.mvc.servlet.http.Cookies;

public final class HeaderUtils {

    private static final String SESSION_ID = "sessionId";

    private HeaderUtils() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    public static Long getSessionId(Cookies cookies) {
        Map<String, Cookie> cookiesMap = cookies.getCookiesMap();
        Cookie findCookie = cookiesMap.get(SESSION_ID);
        if (findCookie != null) {
            return extractSessionId(findCookie);
        }
        return null;
    }

    private static Long extractSessionId(Cookie cookie) {
        if (cookie.value() != null) {
            String sessionId = cookie.value();
            return parseLong(sessionId);
        }
        return null;
    }

    private static Long parseLong(String sessionId) {
        try {
            return Long.valueOf(sessionId);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
