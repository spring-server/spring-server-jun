package project.server.jdbc.core.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import static project.server.jdbc.core.DataSourceUtils.releaseConnection;
import static project.server.jdbc.core.transaction.TransactionSynchronizationManager.bindResource;

@Slf4j
public class DataSourceTransactionManager extends AbstractPlatformTransactionManager {

    private final DataSource dataSource;

    public DataSourceTransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    void doBegin(
        Object transaction,
        TransactionDefinition definition
    ) throws SQLException {
        DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;
        Connection connection = dataSource.getConnection();
        log.info("Get connection.");

        txObject.setConnection(definition.getId(), connection);
        connection.setAutoCommit(false);
        log.info("Set auto-commit false.");
        prepareConnectionForTransaction(connection, definition);

        bindResource(definition.getId(), txObject);
    }

    private void prepareConnectionForTransaction(
        Connection connection,
        TransactionDefinition definition
    ) throws SQLException {
        if (definition != null && definition.isReadOnly()) {
            connection.setReadOnly(true);
        }
    }

    @Override
    public void commit(TransactionStatus status) {
        DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
        Connection connection = txObject.getConnection();

        try {
            connection.commit();
        } catch (Exception exception) {
            throw new RuntimeException();
        } finally {
            releaseConnection(connection, status);
        }
    }

    @Override
    public void rollback(TransactionStatus status) {
        DataSourceTransactionObject txObject = (DataSourceTransactionObject) status.getTransaction();
        Connection connection = txObject.getConnection();

        try {
            connection.rollback();
        } catch (Exception exception) {
            throw new RuntimeException();
        } finally {
            releaseConnection(connection, status);
            log.debug("Rollback, transaction failed.");
        }
    }

    @Override
    protected Object doGetTransaction() {
        return new DataSourceTransactionObject();
    }
}
