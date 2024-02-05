package project.server.jdbc.core.transaction;

import javax.sql.DataSource;

public class JdbcTransactionManager extends DataSourceTransactionManager {

    public JdbcTransactionManager(DataSource dataSource) {
        super(dataSource);
    }
}
