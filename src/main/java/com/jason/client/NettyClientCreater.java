package com.jason.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Netty测试客户端
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 9:47
 */
//@Component("nettyClient")
public class NettyClientCreater { //implements InitializingBean {

//    @Autowired
//    Environment environment;

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // 线程池
//        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 600, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
//        for (int i = 0; i < 10; i++) {
//            threadPool.execute(creat());
//        }
//    }

    /**
     * 创建一个客户端
     * @return: void
     * @date: 2019/5/1 10:10
     */
    public static void createClient () {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .remoteAddress("127.0.0.1", 2333)
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
     * @return: java.lang.Runnable
     * @date: 2019/5/1 10:13
     */
    public Runnable creat() {
        return () -> createClient();
    }

    public static void main(String[] args) {
        new Thread(() -> createClient()).start();
    }
}
