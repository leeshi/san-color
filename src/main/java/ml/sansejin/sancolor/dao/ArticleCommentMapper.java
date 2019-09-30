package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.ArticleComment;
import ml.sansejin.sancolor.entity.ArticleCommentExample;
import org.apache.ibatis.annotations.Param;

public interface ArticleCommentMapper {
    int deleteByExample(ArticleCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    List<ArticleComment> selectByExample(ArticleCommentExample example);

    List<ArticleComment> selectAll();

    ArticleComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByExample(@Param("record") ArticleComment record, @Param("example") ArticleCommentExample example);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    int selectCountByArticleId(Long articleId);
}