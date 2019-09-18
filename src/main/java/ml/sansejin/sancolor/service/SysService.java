package ml.sansejin.sancolor.service;

import ml.sansejin.sancolor.entity.SysLog;
import ml.sansejin.sancolor.entity.SysVisit;

import java.util.List;

/**
 * 日志/访问 统计的相关service
 * @author sansejin
 */
public interface SysService {
    void addLog(SysLog sysLog);

    void addVisit(SysVisit sysVisit);

    int getLogCount();

    int getVisitCount();

    List<SysLog> listAllLog();  //需要全部都显示出来吗？

    List<SysVisit> listAllVisit();  //需要全部都显示出来吗？
}
