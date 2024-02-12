package project.server.jdbc.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionCallback<T> {
    T doInConnection(Connection connection) throws SQLException;
}
