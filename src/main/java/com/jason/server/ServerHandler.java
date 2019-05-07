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

/**
 * Netty服务器Channel处理
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:23
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf in = (ByteBuf)msg;
            // 解析成字符串内容
            String content = in.toString(CharsetUtil.UTF_8);
            System.out.println("服务器收到信息：" + in.toString(CharsetUtil.UTF_8));
//            if (content.contains("id")) {
//                // 客户端注册信道
//                int id = Integer.parseInt(content.substring(content.indexOf(":") + 1));
//                ChannelProcessor.getInstance().addChannel(id, ctx.channel());
//            }
            String action = content.substring(0, content.indexOf("@"));
            String method = content.substring(content.indexOf("@") + 1);
            ActionBase actionBase = (ActionBase) SpringProcessor.getInstance().getBean(action);
            if (actionBase == null) {
                // 容错
                throw new Exception();
            }
            String result = actionBase.dealMessage(method);
            // 返回结果
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            // 返回结果
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer("fail", CharsetUtil.UTF_8));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        // 移除Channel
        ChannelProcessor.getInstance().removeChannel(ctx.channel());
    }
}
