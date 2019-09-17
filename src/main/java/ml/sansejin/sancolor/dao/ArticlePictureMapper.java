package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.ArticlePicture;
import ml.sansejin.sancolor.entity.ArticlePictureExample;

public interface ArticlePictureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticlePicture record);

    int insertSelective(ArticlePicture record);

    List<ArticlePicture> selectByExample(ArticlePictureExample example);

    ArticlePicture selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticlePicture record);

    int updateByPrimaryKey(ArticlePicture record);
}