import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring配置类
 * @author zhuzhenhao
 * @version 1.0.0
 * @date 2019/4/30 19:34
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan({"com.jason"})
public class ApplicationConfig {

}
