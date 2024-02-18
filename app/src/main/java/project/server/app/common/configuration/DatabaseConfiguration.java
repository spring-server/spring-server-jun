package project.server.app.common.configuration;

import java.io.IOException;
import project.server.jdbc.core.ConfigMap;
import project.server.jdbc.core.DriverManager;
import project.server.jdbc.core.jdbc.JdbcTemplate;
import project.server.jdbc.core.transaction.JdbcTransactionManager;
import project.server.jdbc.core.transaction.PlatformTransactionManager;
import project.server.mvc.springframework.annotation.Bean;
import project.server.mvc.springframework.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public ConfigMapLoader configMapLoader() {
        return new ConfigMapLoader();
    }

    @Bean
    public DriverManager driverManager() throws IOException {
        ConfigMap configMap = configMapLoader().getConfigMap();
        return new DriverManager(configMap);
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        return new JdbcTransactionManager(driverManager().getDataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws IOException {
        return new JdbcTemplate(driverManager().getDataSource());
    }
}
