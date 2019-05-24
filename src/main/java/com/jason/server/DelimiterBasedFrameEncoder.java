package com.jason.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * 处理粘包
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/24 15:53
 */
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<ByteBuf> {

    private String delimiter;

    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        // 在响应的数据后面添加分隔符
        // 解析成字符串内容
        String content = msg.toString(CharsetUtil.UTF_8);
        ctx.writeAndFlush(Unpooled.wrappedBuffer((content + delimiter).getBytes()));
    }

}
