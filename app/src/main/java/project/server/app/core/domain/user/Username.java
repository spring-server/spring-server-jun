package project.server.app.core.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Username(String value) {

    private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_\\-]{7,13}$");

    public Username {
        validate(value);
    }

    private void validate(String value) {
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            String message = String.format("올바른 이름을 입력해주세요. Value: %s", value);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
