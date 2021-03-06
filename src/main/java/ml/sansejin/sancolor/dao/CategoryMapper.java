package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.Category;
import ml.sansejin.sancolor.entity.CategoryExample;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}