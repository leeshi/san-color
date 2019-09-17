package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.ArticleInfo;
import ml.sansejin.sancolor.entity.ArticleInfoExample;

public interface ArticleInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    List<ArticleInfo> selectByExample(ArticleInfoExample example);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);
}