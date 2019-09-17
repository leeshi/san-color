package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.SysView;
import ml.sansejin.sancolor.entity.SysViewExample;

public interface SysViewMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysView record);

    int insertSelective(SysView record);

    List<SysView> selectByExample(SysViewExample example);

    SysView selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysView record);

    int updateByPrimaryKey(SysView record);
}