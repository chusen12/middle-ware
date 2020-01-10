package it.chusen.tc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chusen
 * @date 2020/1/10 3:05 下午
 */
@Slf4j
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 各个事务组中子事务状态
     */
    private Map<String, List<String>> transactionTypes = new HashMap<String, List<String>>();

    /**
     * 事务组状态
     */
    private Map<String, Boolean> transactionStatus = new HashMap<String, Boolean>();

    /**
     * 子事务个数
     */
    private Map<String, Integer> transactionCounts = new HashMap<String, Integer>();


    @Override

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        log.info("receive msg: {}: ", msg);
        JSONObject jsonObject = JSON.parseObject(msg);
        // 拿到命令
        String command = jsonObject.getString("command");
        String groupId = jsonObject.getString("groupId");
        String transactionType = jsonObject.getString("transactionType");
        int transactionCount = jsonObject.getIntValue("transactionCount");
        boolean isEnd = jsonObject.getBoolean("isEnd");
        if ("start".equalsIgnoreCase(command)) {
            transactionTypes.put(groupId, new ArrayList<String>());
        } else if ("add".equalsIgnoreCase(command)) {
            // 加入事务组
            transactionTypes.get(groupId).add(transactionType);
            if (isEnd) {
                transactionStatus.put(groupId, true);
                transactionCounts.put(groupId, transactionCount);
            }
            JSONObject result = new JSONObject();
            result.put("groupId", groupId);
            if (transactionStatus.get(groupId) && transactionCounts.get(groupId).equals(transactionCount)) {
                if (transactionTypes.get(groupId).contains("rollback")) {
                    result.put("command", "rollback");
                    sendResult(result);
                } else {
                    result.put("command", "commit");
                    sendResult(result);
                }
            }
        } else {
            // do nothing
        }

    }

    private void sendResult(JSONObject result) {
        for (Channel channel : channelGroup) {
            log.info("发送数据: {}", result.toJSONString());
            channel.writeAndFlush(result.toJSONString());
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
    }
}
