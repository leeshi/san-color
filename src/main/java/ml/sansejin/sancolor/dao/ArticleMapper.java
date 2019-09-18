package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.Article;
import ml.sansejin.sancolor.entity.ArticleExample;

public interface ArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
}