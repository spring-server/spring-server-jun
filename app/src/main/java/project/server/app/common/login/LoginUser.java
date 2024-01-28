package project.server.app.common.login;

import java.util.Objects;

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

    public LoginUser(Session session) {
        this.userId = session.getUserId();
        this.loginIp = null;
        this.valid = session.isValid();
    }

    public Long getUserId() {
        return userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        LoginUser loginUser = (LoginUser) object;
        return userId.equals(loginUser.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
