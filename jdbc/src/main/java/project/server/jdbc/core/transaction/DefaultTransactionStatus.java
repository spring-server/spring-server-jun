package project.server.jdbc.core.transaction;

public class DefaultTransactionStatus extends AbstractTransactionStatus {

    private final Object transaction;
    private final boolean newTransaction;
    private final boolean readOnly;

    public DefaultTransactionStatus(
        Object transaction,
        boolean newTransaction,
        boolean readOnly
    ) {
        this.transaction = transaction;
        this.newTransaction = newTransaction;
        this.readOnly = readOnly;
    }

    @Override
    public Object getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return String.format("%s, newTransaction:%s, readOnly:%s", transaction, newTransaction, readOnly);
    }
}
