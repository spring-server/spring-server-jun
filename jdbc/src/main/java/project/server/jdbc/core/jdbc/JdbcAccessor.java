package project.server.jdbc.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public abstract class JdbcAccessor implements InitializingBean {

    private DataSource dataSource;

    public JdbcAccessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
