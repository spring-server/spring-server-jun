package project.server.jdbc.core;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import static project.server.jdbc.core.transaction.TransactionSynchronizationManager.releaseResource;

@Slf4j
public final class DataSourceUtils {

    public static void releaseConnection(
        DataSource dataSource,
        Connection connection
    ) {
        try {
            doReleaseConnection(connection);
        } catch (SQLException exception) {
            log.error("SQLException.", exception);
        } catch (Throwable exception) {
            log.error("Exception.", exception);
        } finally {
            releaseResource(dataSource);
        }
    }

    public static void doReleaseConnection(Connection connection) throws SQLException {
        if (connection == null) {
            return;
        }
        doCloseConnection(connection);
    }

    public static void doCloseConnection(Connection connection) throws SQLException {
        connection.close();
        log.debug("Connection closed.");
    }
}
