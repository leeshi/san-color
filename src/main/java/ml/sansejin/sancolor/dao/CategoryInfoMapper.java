package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.CategoryInfo;
import ml.sansejin.sancolor.entity.CategoryInfoExample;

public interface CategoryInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CategoryInfo record);

    int insertSelective(CategoryInfo record);

    List<CategoryInfo> selectByExample(CategoryInfoExample example);

    CategoryInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CategoryInfo record);

    int updateByPrimaryKey(CategoryInfo record);
}