package ml.sansejin.sancolor.aspect;

import ml.sansejin.sancolor.entity.SysLog;
import ml.sansejin.sancolor.entity.SysVisit;
import ml.sansejin.sancolor.service.SysService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sansejin
 * @className SysAspect
 * @description TODO
 * @create 10/12/19 9:04 PM
 **/

@Component
@Aspect
@Order(1)
public class SysAspect {
    @Resource
    SysService sysService;

    private final String ALL_VISIT_POINT_CUT = "execution(public * ml.sansejin.sancolor.web.controller.ArticleController.fetchAllArticles(..)) || execution(public * ml.sansejin.sancolor.web.controller.ArticleController.fetchArticle(..))";
    private final String ALL_LOG_POINT_CUT = "execution(public * ml.sansejin.sancolor.web.controller.*Controller.update*(..)) || execution(public * ml.sansejin.sancolor.web.controller.*Controller.delete*(..)) || execution(public * ml.sansejin.sancolor.web.controller.*Controller.add*(..))";


    @Pointcut(ALL_VISIT_POINT_CUT)
    public void pointCutVisit() {
    }

    @Pointcut(ALL_LOG_POINT_CUT)
    public void pointCutLog() {
    }


    /**
     * 当访问一篇文章或者获取所有文章后，插入访问记录
     * 在请求完成后执行
     */
    @After(value = "pointCutVisit()")
    public void afterFetchArticle() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        SysVisit sysVisit = new SysVisit();
        sysVisit.setIp(request.getRemoteAddr());

        sysService.addVisit(sysVisit);

    }

    /**
     * 当对数据库数据进行修改、删除或者增加时，添加一条日志记录
     * 在请求完成后执行
     */
    @After(value = "pointCutLog()")
    public void afterModification() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        System.out.println(request.getHeader("User-Agent"));

        SysLog sysLog = new SysLog();

        sysLog.setIp(request.getRemoteAddr());
        sysLog.setOperate_url(request.getRequestURI());
        sysLog.setOperate_by(request.getHeader("User-Agent"));
        //暂时全部为有效
        sysLog.setIs_effective(true);

        sysService.addLog(sysLog);
    }

}
