package project.server.app.core.web.user.application.service;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import project.server.app.common.configuration.DatabaseConfiguration;
import project.server.app.common.exception.BusinessException;
import project.server.app.common.exception.InvalidParameterException;
import project.server.app.common.login.LoginUser;
import project.server.app.core.domain.user.User;
import project.server.app.core.web.user.application.UserDeleteUseCase;
import project.server.app.core.web.user.application.UserSaveUseCase;
import project.server.app.core.web.user.application.UserSearchUseCase;
import project.server.jdbc.core.exception.DataAccessException;
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
    public Long save(
        String username,
        String password
    ) {
        TransactionStatus txStatus = getTransactionStatus(false);
        log.debug("txStatus:[{}]", txStatus.getTransaction());
        try {
            Long userId = target.save(username, password);
            txManager.commit(txStatus);
            log.debug("Transaction finished.");
            return userId;
        } catch (IllegalArgumentException exception) {
            txManager.rollback(txStatus);
            log.error("{}", exception.getMessage());
            throw new InvalidParameterException();
        } catch (BusinessException | DataAccessException exception) {
            txManager.rollback(txStatus);
            log.error("{}", exception.getMessage());
            throw exception;
        }
    }

    @Override
    public User findById(Long userId) {
        TransactionStatus txStatus = getTransactionStatus(true);
        log.debug("txStatus:[{}]", txStatus.getTransaction());
        try {
            User findUser = target.findById(userId);
            txManager.commit(txStatus);
            log.debug("Transaction finished.");
            return findUser;
        } catch (BusinessException | DataAccessException exception) {
            txManager.rollback(txStatus);
            log.error("{}", exception.getMessage());
            throw exception;
        }
    }

    @Override
    public void delete(LoginUser loginUser) {
        TransactionStatus txStatus = getTransactionStatus(false);
        log.debug("txStatus:[{}]", txStatus.getTransaction());
        try {
            target.delete(loginUser);
            txManager.commit(txStatus);
            log.debug("Transaction finished.");
        } catch (BusinessException | DataAccessException exception) {
            txManager.rollback(txStatus);
            log.error("{}", exception.getMessage());
            throw exception;
        }
    }

    private TransactionStatus getTransactionStatus(boolean readOnly) {
        try {
            return txManager.getTransaction(createTransactionDefinition(readOnly));
        } catch (Exception exception) {
            log.error("{}", exception.getMessage());
            throw new DataAccessException();
        }
    }
}
