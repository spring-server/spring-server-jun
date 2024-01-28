package project.server.app.common.login;

import java.util.Objects;

public class LoginUser {

    private final Long userId;
    private final String username;
    private final String loginIp;
    private boolean valid;

    public LoginUser(
        Long userId,
        String username,
        String loginIp
    ) {
        this.userId = userId;
        this.username = username;
        this.loginIp = loginIp;
    }

    public static LoginUser create(
        String username,
        String password
    ) {
        return new LoginUser(1L, password, "");
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
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
