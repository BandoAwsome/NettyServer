package com.jason.server;

import com.jason.action.ActionBase;
import com.jason.spring.SpringProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty服务器Channel处理
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:23
 */
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String actionParam = (String) msg;
        if (!actionParam.contains("@")) {
            // 丢弃
            return;
        }
        String actionName = actionParam.substring(0, actionParam.indexOf("@"));
        String methodName = actionParam.substring(actionParam.indexOf("@") + 1);
        // 获得bean
        ActionBase action = (ActionBase) SpringProcessor.getInstance().getBean(actionName);
        byte[] returnMsg = (action.dealMessage(methodName) + "\n").getBytes();
        ByteBuf byteBuf = Unpooled.buffer(returnMsg.length);
        byteBuf.writeBytes(returnMsg);
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

}
