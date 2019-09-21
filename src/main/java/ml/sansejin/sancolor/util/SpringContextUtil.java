package ml.sansejin.sancolor.util;

import org.springframework.context.ApplicationContext;

/**
 * @author sansejin
 * @className SpringContextUtil
 * @description TODO
 * @create 9/18/19 9:49 PM
 **/
public class SpringContextUtil {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }
}
