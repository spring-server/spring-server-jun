package project.server.jdbc.core.jdbc;

import javax.sql.DataSource;

public abstract class JdbcAccessor implements InitializingBean {
    private DataSource dataSource;

    public JdbcAccessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
