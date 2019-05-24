package com.jason.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
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
            EventLoopGroup workerGroup = new NioEventLoopGroup(1000);
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 设置为1024队列长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置高低水平位线
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 64*1024))
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .localAddress(new InetSocketAddress(environment.getProperty("netty.server.port", Integer.class)))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            String delimiter = "_$";
                            // 将delimiter设置到DelimiterBasedFrameDecoder中，经过该解码一器进行处理之后，源数据将会
                            // 被按照_$进行分隔，这里1024指的是分隔的最大长度，即当读取到1024个字节的数据之后，若还是未
                            // 读取到分隔符，则舍弃当前数据段，因为其很有可能是由于码流紊乱造成的
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.wrappedBuffer(delimiter.getBytes())));
                            // 这是我们自定义的一个编码器，主要作用是在返回的响应数据最后添加分隔符
                            ch.pipeline().addLast(new DelimiterBasedFrameEncoder(delimiter));
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
