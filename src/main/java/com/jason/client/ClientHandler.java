package com.jason.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Netty客户端Handler
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 9:53
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // channel被激活后，客户端开始压测
        int num = 1;
        for (int i = 0; i < 1000000; i++) {
            ByteBuf byteBuf = ctx.alloc().buffer(4);
            byteBuf.writeCharSequence("pressure", CharsetUtil.UTF_8);
            // 积压一定数量后一次性flush
            if (num <= 2000) {
                ctx.channel().write(byteBuf);
                num++;
            } else {
                ctx.channel().write(byteBuf);
                ctx.channel().flush();
                num = 1;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端收到信息：" + msg.toString());
    }
}
