package project.server.jdbc.core.transaction;

import static java.util.UUID.randomUUID;

public class DefaultTransactionDefinition implements TransactionDefinition {

    private final String id;
    private final boolean readOnly;

    public DefaultTransactionDefinition() {
        this.id = randomUUID().toString();
        this.readOnly = false;
    }

    public DefaultTransactionDefinition(boolean readOnly) {
        this.id = randomUUID().toString();
        this.readOnly = readOnly;
    }

    public static DefaultTransactionDefinition createTransactionDefinition(boolean readOnly) {
        return new DefaultTransactionDefinition(readOnly);
    }

    @Override
    public String getId() {
        return id;
    }
}
