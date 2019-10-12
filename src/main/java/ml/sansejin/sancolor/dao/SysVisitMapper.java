package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.SysVisit;
import ml.sansejin.sancolor.entity.SysVisitExample;

public interface SysVisitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysVisit record);

    int insertSelective(SysVisit record);

    List<SysVisit> selectByExample(SysVisitExample example);

    SysVisit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysVisit record);

    int updateByPrimaryKey(SysVisit record);

    int selectCountOfVisit();
}