import com.jason.server.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 启动类
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:39
 */
public class Main {

    /** Netty监听端口 */
    private static final int PORT = 2333;

    public static void main(String[] args) {
        // 启动Spring
        SpringProcessor.getInstance().init();
        // 启动Netty
        startUp();

    }

    /**
     * 启动Netty服务器
     * @return: void
     * @date: 2019/4/30 19:52
     */
    public static void startUp() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(PORT))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 加入自定义Handler
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });
            // 阻塞当前线程直到完成绑定
            ChannelFuture future = serverBootstrap.bind().sync();
            System.out.println("-------------------Netty服务器启动");
            // 主线程退出，子线程真正监听和接受请求
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
