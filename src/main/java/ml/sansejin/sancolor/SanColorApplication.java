package ml.sansejin.sancolor;

import ml.sansejin.sancolor.util.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@MapperScan("ml.sansejin.sancolor.dao")
@SpringBootApplication
public class SanColorApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SanColorApplication.class, args);

        SpringContextUtil.setApplicationContext(context);
    }

}
