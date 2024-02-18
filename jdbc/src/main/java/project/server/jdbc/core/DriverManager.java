package project.server.jdbc.core;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class DriverManager {

    private final DataSource dataSource;

    public DriverManager(ConfigMap configMap) {
        this.dataSource = createDataSource(configMap);
    }

    private DataSource createDataSource(ConfigMap configMap) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(configMap.getDriverClassName());
        dataSource.setUrl(configMap.getUrl());
        dataSource.setUsername(configMap.getUsername());
        dataSource.setPassword(configMap.getPassword());
        return dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
