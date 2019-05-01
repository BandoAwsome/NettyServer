package com.jason.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty测试客户端
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 9:47
 */
@Component("nettyClient")
public class NettyClientCreater implements InitializingBean {

    @Autowired
    Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        // Spring启动后自动创建10个客户端进程
        for (int i = 0; i < 10; i++) {
            creat();
        }
    }

    /**
     * 创建一个客户端
     * @return: void
     * @date: 2019/5/1 10:10
     */
    public void createClient () {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .remoteAddress("127.0.0.1", environment.getProperty("netty.server.port", Integer.class))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            System.out.println("--------------客户端：" + Thread.currentThread().toString() + " 启动");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异步创建一个客户端
     * @return: void
     * @date: 2019/5/1 10:13
     */
    public void creat() {
        new Thread(() -> createClient()).start();
    }
}
