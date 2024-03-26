package project.server.app.core.web.user.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import project.server.app.core.domain.user.Deleted;
import project.server.app.core.domain.user.User;
import project.server.jdbc.core.jdbc.RowMapper;
import project.server.mvc.springframework.annotation.Component;

@Component
public class UserRowMapper implements RowMapper<User> {

    private static final String USER_ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String CREATED_AT = "created_at";
    private static final String LAST_MODIFIED_AT = "last_modified_at";
    private static final String DELETED = "deleted";

    @Override
    public User mapRow(ResultSet rs) throws SQLException {
        Long id = rs.getLong(USER_ID);
        String username = rs.getString(USERNAME);
        String password = rs.getString(PASSWORD);
        LocalDateTime createdAt = rs.getTimestamp(CREATED_AT).toLocalDateTime();
        LocalDateTime lastModifiedAt = rs.getTimestamp(LAST_MODIFIED_AT) != null
            ? rs.getTimestamp(LAST_MODIFIED_AT).toLocalDateTime() : null;
        Deleted deleted = Deleted.valueOf(rs.getString(DELETED));
        return new User(id, username, password, createdAt, lastModifiedAt, deleted);
    }
}
