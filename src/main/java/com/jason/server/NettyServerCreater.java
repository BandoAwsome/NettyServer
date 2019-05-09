package com.jason.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Netty服务器
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 11:03
 */
@Component("nettyServer")
public class NettyServerCreater implements InitializingBean {

    @Autowired
    Environment environment;

    /** 服务器只能被初始化一次 */
    private static volatile boolean isInit = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        startUp();
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
        new Thread( () -> { try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(environment.getProperty("netty.server.port", Integer.class)))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 加入自定义Handler
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            // 阻塞当前线程直到完成绑定
            ChannelFuture future = serverBootstrap.bind().sync();
            System.out.println("-------------------Netty服务器启动");
            isInit = true;
            // 主线程退出，子线程真正监听和接受请求
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}).start();
    }
}
