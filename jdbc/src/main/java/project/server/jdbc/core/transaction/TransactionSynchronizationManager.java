package project.server.jdbc.core.transaction;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TransactionSynchronizationManager {

    private TransactionSynchronizationManager() {
        throw new AssertionError("올바른 방식으로 생성자를 호출해주세요.");
    }

    private static final ThreadLocal<Map<DataSource, Object>> factory;

    static {
        factory = new ThreadLocal<>();
        factory.set(new HashMap<>());
    }

    public static void bindResource(
        DataSource key,
        Object value
    ) {
        Map<DataSource, Object> transactionMap = factory.get();
        transactionMap.put(key, value);
        log.debug("Put {} into threadlocal.", key);
    }

    public static void releaseResource(DataSource key) {
        Map<DataSource, Object> transactionMap = factory.get();
        transactionMap.remove(key);
        log.debug("Remove {} from threadlocal.", key);
    }
}
