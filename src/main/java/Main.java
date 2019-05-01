import com.jason.spring.SpringProcessor;

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
    }
}