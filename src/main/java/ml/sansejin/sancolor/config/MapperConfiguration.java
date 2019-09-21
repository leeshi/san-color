package ml.sansejin.sancolor.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author sansejin
 * @className MapperConfiguration
 * @description TODO
 * @create 9/18/19 9:28 PM
 **/
@Configuration
public class MapperConfiguration {
/*
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        //配置xml扫描路径
        mapperScannerConfigurer.setBasePackage("ml.sansejin.sancolor.dao");

        return mapperScannerConfigurer;
    }
*/

/*    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        //配置SessionFactory
        sqlSessionFactoryBean.setDataSource(SpringContextUtil.getBean("dataSource"));

        return sqlSessionFactoryBean;
    }*/
}
