package util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chusen
 * @date 2020/1/10 5:29 下午
 */
public class Task {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitTask() {
        lock.lock();
        try {
            condition.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalTask() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();

        }
    }
}
