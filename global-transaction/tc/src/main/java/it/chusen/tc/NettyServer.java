package it.chusen.tc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author chusen
 * @date 2020/1/10 2:43 下午
 */
public class NettyServer {
    public void start(String host, int port) throws InterruptedException {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boosGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("handler", new MyServerHandler());
                        }
                    });
            server.bind(host, port).sync();
        } finally {
            boosGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();

        }
    }
}
