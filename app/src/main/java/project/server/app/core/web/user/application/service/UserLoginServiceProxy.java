package project.server.app.core.web.user.application.service;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import project.server.app.common.configuration.DatabaseConfiguration;
import project.server.app.common.login.Session;
import project.server.app.core.web.user.application.UserLoginUseCase;
import static project.server.jdbc.core.transaction.DefaultTransactionDefinition.createTransactionDefinition;
import project.server.jdbc.core.transaction.PlatformTransactionManager;
import project.server.jdbc.core.transaction.TransactionStatus;
import project.server.mvc.springframework.annotation.Component;

@Slf4j
@Component
public class UserLoginServiceProxy implements UserLoginUseCase {

    private final PlatformTransactionManager txManager;
    private final UserLoginService target;

    public UserLoginServiceProxy(
        DatabaseConfiguration dbConfiguration,
        UserLoginService target
    ) throws IOException {
        this.txManager = dbConfiguration.transactionManager();
        this.target = target;
    }

    @Override
    public Session login(
        String username,
        String password
    ) {
        TransactionStatus txStatus = getTransactionStatus(false);
        log.debug("txStatus:{}", txStatus);
        try {
            Session findSession = target.login(username, password);
            txManager.commit(txStatus);
            return findSession;
        } catch (Exception exception) {
            txManager.rollback(txStatus);
            throw new RuntimeException();
        }
    }

    @Override
    public Session findSessionById(Long userId) {
        try {
            return target.findSessionById(userId);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    private TransactionStatus getTransactionStatus(boolean readOnly) {
        try {
            return txManager.getTransaction(createTransactionDefinition(readOnly));
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
