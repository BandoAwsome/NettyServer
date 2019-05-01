package com.jason.client;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Channel管理器
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/5/1 11:57
 */
public class ChannelProcessor {

    /** 单例 */
    private static final ChannelProcessor instance = new ChannelProcessor();

    /** id - channel */
    private Map<Integer, Channel> id2ChannelMap = new ConcurrentHashMap<>();

    /** channel - id */
    private Map<Channel, Integer> channel2IdMap = new ConcurrentHashMap<>();

    private ChannelProcessor() { }

    public static ChannelProcessor getInstance() {
        return instance;
    }

    /**
     * 信道增加，并返回唯一Id
     * @param channel
     * @return: int
     * @date: 2019/5/1 12:03
     */
    public void addChannel(int id, Channel channel) {
        if (channel == null) {
            // 容错
            return;
        }
        id2ChannelMap.put(id, channel);
        channel2IdMap.put(channel, id);
    }

    /**
     * 获得Id
     * @param channel
     * @return: int
     * @date: 2019/5/1 14:27
     */
    public int getId(Channel channel) {
        if (!channel2IdMap.containsValue(channel)) {
            return 0;
        }
        return channel2IdMap.get(channel);
    }

    /**
     * 获得channel
     * @param id
     * @return: io.netty.channel.Channel
     * @date: 2019/5/1 14:28
     */
    public Channel getChannel(int id) {
        if (!id2ChannelMap.containsValue(id)) {
            return null;
        }
        return id2ChannelMap.get(id);
    }

    /**
     * 移除Channel缓存
     * @param channel
     * @return: void
     * @date: 2019/5/1 12:08
     */
    public void removeChannel(Channel channel) {
        int id = getId(channel);
        if (id > 0) {
            id2ChannelMap.remove(new Integer(id));
        }
        channel2IdMap.remove(channel);
    }

}
