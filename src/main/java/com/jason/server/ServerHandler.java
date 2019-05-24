package com.jason.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty服务器Channel处理
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:23
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private long pressStartTime;

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (atomicInteger.get() == 0) {
            // 标志压测开始
            pressStartTime = System.currentTimeMillis();
        }
        atomicInteger.incrementAndGet();

        if (atomicInteger.get() % 100000 == 0) {
            System.out.println("处理了消息：" + atomicInteger.get() + ",时间：" + (System.currentTimeMillis() - pressStartTime) / 1000 + "s");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

}
