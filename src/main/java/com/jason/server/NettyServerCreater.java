package com.jason.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Netty服务器
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 11:03
 */
@Slf4j
@Component("nettyServer")
public class NettyServerCreater implements InitializingBean {

    /** 服务器只能被初始化一次 */
    private static volatile boolean isInit = false;

    /** 端口 */
    private final int port = 2333;

    @Override
    public void afterPropertiesSet() {
        new Thread(() -> startUp()).start();
    }

    /**
     * 启动Netty服务器
     * @return: void
     * @date: 2019/4/30 19:52
     */
    public void startUp() {
        if (isInit) {
            // 已被初始化
            return;
        }
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(100);
        ServerHandler serverHandler = new ServerHandler();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 设置为1024队列长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 设置高低水平位线
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 64*1024))
//                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
//                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast("decoder", new StringDecoder());
                        // 业务线程和IO线程分离
                        ch.pipeline().addLast(new DefaultEventExecutor(Executors.newCachedThreadPool()), serverHandler);
                    }
                });
        try {
            // 阻塞当前线程直到完成绑定
            ChannelFuture future = serverBootstrap.bind().sync();
            log.info("Netty服务器启动, 端口: " + port);
            isInit = true;
            // 主线程退出，子线程真正监听和接受请求
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
