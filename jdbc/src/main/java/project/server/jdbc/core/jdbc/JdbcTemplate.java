package project.server.jdbc.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import project.server.jdbc.core.exception.DataAccessException;

@Slf4j
public class JdbcTemplate extends JdbcAccessor implements JdbcOperations {

    public JdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> T queryForObject(ConnectionCallback<T> action) {
        try (Connection conn = getConnection()) {
            return action.doInConnection(conn);
        } catch (SQLException exception) {
            log.error("SQLException: {}", exception.getMessage());
            throw new DataAccessException();
        }
    }

    @Override
    public <T> T queryForObject(
        String sql,
        PreparedStatementCallback<T> action
    ) {
        return queryForObject(conn -> {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                return action.doInPreparedStatement(pstmt);
            } catch (SQLException exception) {
                log.error("SQLException: {}", exception.getMessage());
                throw new DataAccessException();
            }
        });
    }

    @Override
    public <T> T queryForObject(
        String sql,
        PreparedStatementSetter pss,
        ResultSetExtractor<T> rse
    ) {
        return queryForObject(sql, pstmt -> {
            pss.setValues(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rse.extractData(rs);
            } catch (SQLException exception) {
                log.error("SQLException: {}", exception.getMessage());
                throw new DataAccessException();
            }
        });
    }

    @Override
    public <T> List<T> queryForList(
        String sql,
        RowMapper<T> rowMapper,
        Object... params
    ) {
        return queryForObject(
            sql, pstmt ->
                setParameters(pstmt, params), rs -> {
                List<T> results = new ArrayList<>();
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs));
                }
                return results;
            }
        );
    }

    @Override
    public void execute(String sql) {
        try (
            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.executeUpdate();
        } catch (SQLException exception) {
            log.error("SQLException: {}", exception.getMessage());
            throw new DataAccessException();
        }
    }

    private void setParameters(
        PreparedStatement pstmt,
        Object... params
    ) {
        try {
            for (int index = 0; index < params.length; index++) {
                pstmt.setObject(index + 1, params[index]);
            }
        } catch (SQLException exception) {
            log.error("SQLException: {}", exception.getMessage());
            throw new DataAccessException();
        }
    }

    @Override
    public void afterPropertiesSet() {
        log.info("AfterProperties.");
    }
}
