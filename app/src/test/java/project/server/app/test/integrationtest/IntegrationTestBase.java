package project.server.app.test.integrationtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import project.server.app.core.domain.user.UserRepository;
import project.server.app.core.web.user.persistence.UserPersistenceRepository;
import project.server.mvc.springframework.context.ApplicationContext;
import static project.server.mvc.springframework.context.ApplicationContext.getBean;
import project.server.mvc.springframework.context.ApplicationContextProvider;

public abstract class IntegrationTestBase {

    protected final UserRepository userRepository;

    protected IntegrationTestBase() {
        try {
            ApplicationContextProvider provider = new ApplicationContextProvider();
            provider.setApplicationContext(new ApplicationContext("project"));
            this.userRepository = getBean(UserPersistenceRepository.class);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @BeforeEach
    void setUp() {
        userRepository.clear();
    }
}
