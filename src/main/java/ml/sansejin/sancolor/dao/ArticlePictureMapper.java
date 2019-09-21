package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.ArticlePicture;
import ml.sansejin.sancolor.entity.ArticlePictureExample;
import org.apache.ibatis.annotations.Param;

public interface ArticlePictureMapper {
    int deleteByExample(ArticlePictureExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticlePicture record);

    int insertSelective(ArticlePicture record);

    List<ArticlePicture> selectByExample(ArticlePictureExample example);

    ArticlePicture selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticlePicture record, @Param("example") ArticlePictureExample example);

    int updateByExample(@Param("record") ArticlePicture record, @Param("example") ArticlePictureExample example);

    int updateByPrimaryKeySelective(ArticlePicture record);

    int updateByPrimaryKey(ArticlePicture record);
}