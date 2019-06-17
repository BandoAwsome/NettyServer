package com.jason.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class TSession {

    private Channel channel;

    private Map<Object, Object> values = new HashMap();

    public TSession(Channel channel) {
        this.channel = channel;
    }


    public Channel getChannel() {
        return channel;
    }

    public void setAttribute(Object key, Object value) {
        this.values.put(key, value);
    }

    public Object getAttribute(Object key) {
        return this.values.get(key);
    }

}
