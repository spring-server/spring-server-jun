package project.server.jdbc.core;

import java.sql.Connection;
import java.util.Objects;

public class ConnectionHolder {

    private String uuid;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public String getId() {
        return uuid;
    }

    public void setId(String uniqueId) {
        this.uuid = uniqueId;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ConnectionHolder that = (ConnectionHolder) object;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
