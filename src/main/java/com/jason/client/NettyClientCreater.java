package com.jason.client;

import com.jason.server.DelimiterBasedFrameEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Netty测试客户端
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 9:47
 */
public class NettyClientCreater {

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
                            String delimiter = "_$";
                            // 对服务端返回的消息通过_$进行分隔，并且每次查找的最大大小为1024字节
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(delimiter.getBytes())));
                            // 对客户端发送的数据进行编码，这里主要是在客户端发送的数据最后添加分隔符
                            ch.pipeline().addLast(new DelimiterBasedFrameEncoder(delimiter));
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 线程池
        int num = 1;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 600, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        for (int i = 0; i < num; i++) {
            threadPool.execute(() -> createClient());
        }
    }
}
