package project.server.app.common.login;

import java.util.Objects;
import project.server.app.core.domain.user.User;

public class LoginUser {

    private final Long userId;
    private final String loginIp;
    private boolean valid;

    public LoginUser(
        Long userId,
        String loginIp
    ) {
        this.userId = userId;
        this.loginIp = loginIp;
    }

    public LoginUser(User user) {
        this.userId = user.getId();
        this.loginIp = null;
        this.valid = true;
    }

    public LoginUser(Session session) {
        this.userId = session.getUserId();
        this.loginIp = null;
        this.valid = session.isValid();
    }

    public static LoginUser create(
        String username,
        String password
    ) {
        return null;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        LoginUser loginUser = (LoginUser) object;
        return userId.equals(loginUser.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
