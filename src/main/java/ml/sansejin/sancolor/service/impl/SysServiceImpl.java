package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dao.SysLogMapper;
import ml.sansejin.sancolor.dao.SysVisitMapper;
import ml.sansejin.sancolor.entity.SysLog;
import ml.sansejin.sancolor.entity.SysVisit;
import ml.sansejin.sancolor.service.SysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sansejin
 * @className SysServiceImpl
 * @description TODO
 * @create 10/12/19 8:36 PM
 **/

@Service
public class SysServiceImpl implements SysService {
    @Resource
    SysLogMapper sysLogMapper;

    @Resource
    SysVisitMapper sysVisitMapper;

    /**
     * 记录操作者
     * @param sysLog
     */
    @Override
    public void addLog(SysLog sysLog) {
        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * 记录访问者
     * @param sysVisit
     */
    @Override
    public void addVisit(SysVisit sysVisit) {
        sysVisitMapper.insertSelective(sysVisit);
    }

    @Override
    public int getLogCount() {
        return sysLogMapper.selectCountOfLog();
    }

    @Override
    public int getVisitCount() {
        return sysVisitMapper.selectCountOfVisit();
    }

    @Override
    public List<SysLog> listAllLog() {
        return null;
    }

    @Override
    public List<SysVisit> listAllVisit() {
        return null;
    }
}
