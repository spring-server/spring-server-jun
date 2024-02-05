package project.server.jdbc.core;

import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import project.server.jdbc.core.transaction.DataSourceTransactionObject;
import project.server.jdbc.core.transaction.TransactionStatus;
import static project.server.jdbc.core.transaction.TransactionSynchronizationManager.releaseResource;

@Slf4j
public final class DataSourceUtils {

    public static void releaseConnection(
        Connection connection,
        TransactionStatus status
    ) {
        try {
            doReleaseConnection(connection);
        } catch (SQLException exception) {
            log.debug("Could not close JDBC Connection.", exception);
        } catch (Throwable exception) {
            log.debug("Unexpected exception when closing JDBC Connection", exception);
        } finally {
            DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
            releaseResource(txObject.getId());
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
