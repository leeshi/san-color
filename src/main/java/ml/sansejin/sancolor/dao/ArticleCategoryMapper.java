package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.ArticleCategory;
import ml.sansejin.sancolor.entity.ArticleCategoryExample;

public interface ArticleCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleCategory record);

    int insertSelective(ArticleCategory record);

    List<ArticleCategory> selectByExample(ArticleCategoryExample example);

    ArticleCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleCategory record);

    int updateByPrimaryKey(ArticleCategory record);
}