package com.jason;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class SessionUtil {

    public static int REQ_COUNT = 0;

    public static void setReqCount(int reqCount) {
        REQ_COUNT = reqCount;
    }

    private static final AttributeKey<TSession> SESSION_KEY = AttributeKey.valueOf("session-key");

    public static final boolean createChannelSession(Channel channel) {
        Attribute sessionAttr = channel.attr(SESSION_KEY);
        return sessionAttr.compareAndSet(null, new TSession(channel));
    }

    public final static TSession getChannelSession(Channel channel) {
        Attribute sessionAttr = channel.attr(SESSION_KEY);
        return (TSession) sessionAttr.get();
    }

}
