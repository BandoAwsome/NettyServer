import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring相关单例
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:27
 */
public class SpringProcessor {

    /** 单例 */
    private static final SpringProcessor instance = new SpringProcessor();

    private SpringProcessor() {}

    public static SpringProcessor getInstance() {
        return instance;
    }

    /**
     * 初始化
     * @return: void
     * @date: 2019/4/30 19:32
     */
    public void init() {
        AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext();
        springContext.register(ApplicationConfig.class);
        System.out.println("Spring启动");
    }

}
