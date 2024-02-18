package project.server.jdbc.core.transaction;

public interface TransactionDefinition {
    String getId();

    default boolean isReadOnly() {
        return false;
    }
}
