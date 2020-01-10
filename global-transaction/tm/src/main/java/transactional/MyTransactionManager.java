package transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chusen
 * @date 2020/1/10 5:15 下午
 */
@Component
public class MyTransactionManager {
    private static ThreadLocal<MyTransaction> current = new ThreadLocal<>();

    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();

    private static ThreadLocal<Integer> transactionCount = new ThreadLocal<>();


    public static Map<String, MyTransaction> MY_TRANSACTION_MAP = new HashMap<>();


    public static MyTransaction getMyTransaction(String groupId) {
        return MY_TRANSACTION_MAP.get(groupId);
    }

    public static Integer getTransactionCount() {
        return transactionCount.get();
    }

    public static void setTransactionCount(int i) {
        transactionCount.set(i);
    }

    public static Integer addTransactionCount() {
        int i = (transactionCount.get() == null ? 0 : transactionCount.get() + 1);
        transactionCount.set(i);
        return i;
    }

    public static MyTransaction getCurrent() {
        return current.get();
    }

    public static String getCurrentGroupId() {
        return currentGroupId.get();
    }
}
