package project.server.jdbc.core.transaction;

import java.sql.SQLException;

public abstract class AbstractPlatformTransactionManager implements PlatformTransactionManager {

    @Override
    public final TransactionStatus getTransaction(TransactionDefinition definition) throws SQLException {
        Object transaction = doGetTransaction();
        return startTransaction(definition, transaction);
    }

    private TransactionStatus startTransaction(
        TransactionDefinition definition,
        Object transaction
    ) throws SQLException {
        doBegin(transaction, definition);
        return new DefaultTransactionStatus(transaction, true, false);
    }

    abstract void doBegin(Object transaction, TransactionDefinition definition) throws SQLException;

    protected abstract Object doGetTransaction();
}
