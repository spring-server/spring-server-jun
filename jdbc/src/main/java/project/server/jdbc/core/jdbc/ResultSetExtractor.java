package project.server.jdbc.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetExtractor<T> {
    T extractData(ResultSet resultSet) throws SQLException;
}
