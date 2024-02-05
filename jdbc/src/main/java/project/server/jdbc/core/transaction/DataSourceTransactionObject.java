package project.server.jdbc.core.transaction;

import java.sql.Connection;
import project.server.jdbc.core.ConnectionHolder;

public class DataSourceTransactionObject {

    private final ConnectionHolder connectionHolder;

    public DataSourceTransactionObject() {
        this.connectionHolder = new ConnectionHolder();
    }

    public String getId() {
        return connectionHolder.getId();
    }

    public Connection getConnection() {
        return connectionHolder.getConnection();
    }

    public void setConnection(
        String uniqueId,
        Connection connection
    ) {
        this.connectionHolder.setId(uniqueId);
        this.connectionHolder.setConnection(connection);
    }

    @Override
    public String toString() {
        return String.format("id: %s", connectionHolder.getId());
    }
}
