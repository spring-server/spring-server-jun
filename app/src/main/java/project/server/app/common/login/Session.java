package project.server.app.common.login;

import java.time.LocalDateTime;
import java.util.Objects;

public record Session(
    Long userId,
    String sessionId,
    LocalDateTime expiredAt
) {

    public boolean isValid(LocalDateTime expiredAt) {
        return this.expiredAt.isAfter(expiredAt);
    }

    public String getUserIdAsString() {
        return userId.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Session session = (Session) object;
        return sessionId.equals(session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    @Override
    public String toString() {
        return String.format("userId:%s, sessionId:%s, expiredAt:%s", userId, sessionId, expiredAt);
    }
}
