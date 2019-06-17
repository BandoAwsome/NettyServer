package com.jason.client;

import com.jason.session.SessionUtil;
import com.jason.session.TSession;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Netty测试客户端
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 9:47
 */
public class NettyClientCreater {

    static TSession session = null;

    /**
     * 创建一个客户端
     * @return: void
     * @date: 2019/5/1 10:10
     */
    public static void createClient () {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .remoteAddress("127.0.0.1", 2333)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    if (!SessionUtil.createChannelSession(channelFuture.channel())) {
                        channelFuture.channel().close();
                    }

                    session = SessionUtil.getChannelSession(channelFuture.channel());
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        // 线程池
        createClient();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String readLine = console.readLine();
            if (!StringUtils.isEmpty(readLine)) {
                SessionUtil.REQ_COUNT = Integer.valueOf(readLine);
                String split = System.getProperty("line.separator");
                for (int i = 1; i <= SessionUtil.REQ_COUNT; i++) {
                    byte[] msg = ("客户端消息:" + split).getBytes();
                    ByteBuf byteBuf = Unpooled.buffer(msg.length);
                    byteBuf.writeBytes(msg);
                    session.getChannel().write(byteBuf);
                    if (i % 2000 == 0) {
                        session.getChannel().flush();
                    }
                }
            } else {
                System.out.println("end");
                break;
            }
        }

    }
}
