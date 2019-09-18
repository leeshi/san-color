package ml.sansejin.sancolor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ml.sansejin.sancolor.dao")
public class SanColorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SanColorApplication.class, args);
    }

}
