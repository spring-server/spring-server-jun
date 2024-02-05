package project.server.jdbc.core;

public class ConfigMap {

    private SpringConfig spring;

    public SpringConfig getSpring() {
        return spring;
    }

    public String getDriverClassName() {
        return spring.getDriverClassName();
    }

    public String getUrl() {
        return spring.getUrl();
    }

    public String getUsername() {
        return spring.getUsername();
    }

    public String getPassword() {
        return spring.getPassword();
    }

    static class SpringConfig {
        private DatasourceConfig datasource;

        public DatasourceConfig getDatasource() {
            return datasource;
        }

        public String getDriverClassName() {
            return datasource.getDriverClassName();
        }

        public String getUrl() {
            return datasource.getUrl();
        }

        public String getUsername() {
            return datasource.getUsername();
        }

        public String getPassword() {
            return datasource.getPassword();
        }

        @Override
        public String toString() {
            return String.format("dataSource:%s", datasource);
        }

        static class DatasourceConfig {
            private String driverClassName;
            private String url;
            private String username;
            private String password;

            public String getDriverClassName() {
                return driverClassName;
            }

            public String getUrl() {
                return url;
            }

            public String getUsername() {
                return username;
            }

            public String getPassword() {
                return password;
            }

            @Override
            public String toString() {
                return String.format(
                    "driverClassName:%s, url:%s, username:%s, password:%s", driverClassName, url, username, password
                );
            }
        }
    }

    @Override
    public String toString() {
        return String.valueOf(spring);
    }
}
