package project.server.jdbc.core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementCallback<T> {
    T doInPreparedStatement(PreparedStatement pstmt) throws SQLException;
}
