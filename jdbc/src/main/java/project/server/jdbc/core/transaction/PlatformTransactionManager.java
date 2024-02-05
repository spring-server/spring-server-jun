package project.server.jdbc.core.transaction;

import java.sql.SQLException;

public interface PlatformTransactionManager extends TransactionManager {
    TransactionStatus getTransaction(TransactionDefinition definition) throws SQLException;

    void commit(TransactionStatus status);

    void rollback(TransactionStatus status);
}
