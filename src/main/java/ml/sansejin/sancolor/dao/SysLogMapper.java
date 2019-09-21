package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.SysLog;
import ml.sansejin.sancolor.entity.SysLogExample;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    List<SysLog> selectByExample(SysLogExample example);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}