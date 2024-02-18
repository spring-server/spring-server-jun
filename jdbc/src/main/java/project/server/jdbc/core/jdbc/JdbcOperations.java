package project.server.jdbc.core.jdbc;

import java.util.List;

public interface JdbcOperations {
    <T> T queryForObject(ConnectionCallback<T> action);

    <T> T queryForObject(String sql, PreparedStatementCallback<T> action);

    <T> T queryForObject(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse);

    <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... params);

    void execute(String sql);
}
