package transactional;

import util.Task;

/**
 * @author chusen
 * @date 2020/1/10 5:14 下午
 */
public class MyTransaction {
    private String groupId;

    private String transactionId;

    private TransactionType transactionType;

    private Task task;

    public MyTransaction(String groupId, String transactionId) {
        this.groupId = groupId;
        this.transactionId = transactionId;
    }

    public MyTransaction(String groupId, String transactionId, TransactionType transactionType) {
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
