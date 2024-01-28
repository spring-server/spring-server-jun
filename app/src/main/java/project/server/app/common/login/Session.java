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

    public Long getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public boolean isValid() {
        return expired.isAfter(LocalDateTime.now());
    }

    public String getUserIdAsString() {
        return userId.toString();
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

    @Override
    public String toString() {
        return "Session{" +
            "userId=" + userId +
            ", sessionId='" + sessionId + '\'' +
            ", expired=" + expired +
            '}';
    }
}
