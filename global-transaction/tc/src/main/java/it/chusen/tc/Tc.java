package it.chusen.tc;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chusen
 * @date 2020/1/10 3:39 下午
 */
@Slf4j
public class Tc {
    public static void main(String[] args) throws InterruptedException {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start("localhost", 10000);
        log.info("服务启动成功!");
    }
}
