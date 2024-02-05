package project.server.app.core.web.user.application.service;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import project.server.app.common.configuration.DatabaseConfiguration;
import project.server.app.common.login.LoginUser;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserDeleteUseCase;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.UserSearchUseCase;
import static project.server.jdbc.core.transaction.DefaultTransactionDefinition.createTransactionDefinition;
import project.server.jdbc.core.transaction.PlatformTransactionManager;
import project.server.jdbc.core.transaction.TransactionStatus;
import project.server.mvc.springframework.annotation.Component;

@Slf4j
@Component
public class UserServiceProxy implements UserSaveUseCase, UserSearchUseCase, UserDeleteUseCase {

    private final PlatformTransactionManager txManager;
    private final UserService target;

    public UserServiceProxy(
        DatabaseConfiguration dbConfiguration,
        UserService target
    ) throws IOException {
        this.txManager = dbConfiguration.transactionManager();
        this.target = target;
    }

    @Override
    public User save(User user) {
        TransactionStatus txStatus = getTransactionStatus(false);
        log.info("txStatus:[{}]", txStatus.getTransaction());
        try {
            User newUser = target.save(user);
            txManager.commit(txStatus);
            return newUser;
        } catch (Exception exception) {
            txManager.rollback(txStatus);
            throw new RuntimeException();
        }
    }

    @Override
    public User findById(Long userId) {
        TransactionStatus txStatus = getTransactionStatus(true);
        log.info("txStatus:[{}]", txStatus.getTransaction());
        try {
            return target.findById(userId);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(LoginUser loginUser) {
        TransactionStatus txStatus = getTransactionStatus(false);
        log.info("txStatus:[{}]", txStatus.getTransaction());
        try {
            target.delete(loginUser);
            txManager.commit(txStatus);
        } catch (Exception exception) {
            txManager.rollback(txStatus);
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
