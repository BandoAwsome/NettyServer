package com.jason.server;

import com.jason.action.ActionBase;
import com.jason.client.ChannelProcessor;
import com.jason.spring.SpringProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty服务器Channel处理
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:23
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private Date pressStartTime;

    private AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 压测100000次
        ByteBuf in = (ByteBuf)msg;
        // 解析成字符串内容
        String content = in.toString(CharsetUtil.UTF_8);
        System.out.println("服务器收到信息：" + content);
        System.out.println("包的序号：" + atomicInteger.incrementAndGet());
        System.out.println("所需时间：" + (new Date().getTime() - pressStartTime.getTime()));
        in.release();
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
//    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 标志压测开始
        pressStartTime = new Date();
    }

}
