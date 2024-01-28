package project.server.app.common.login;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private final Long userId;
    private final String sessionId;
    private final LocalDateTime expired;

    public Session(
        Long userId,
        String sessionId,
        LocalDateTime expired
    ) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.expired = expired;
    }

    public String getUserId() {
        return userId.toString();
    }

    public String getSessionId() {
        return sessionId;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return sessionId.equals(session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
