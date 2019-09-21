package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.Article;
import ml.sansejin.sancolor.entity.ArticleExample;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {
    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
}