package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.ArticleUser;
import ml.sansejin.sancolor.entity.ArticleUserExample;
import org.apache.ibatis.annotations.Param;

public interface ArticleUserMapper {
    int deleteByExample(ArticleUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ArticleUser record);

    int insertSelective(ArticleUser record);

    List<ArticleUser> selectByExample(ArticleUserExample example);

    ArticleUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ArticleUser record, @Param("example") ArticleUserExample example);

    int updateByExample(@Param("record") ArticleUser record, @Param("example") ArticleUserExample example);

    int updateByPrimaryKeySelective(ArticleUser record);

    int updateByPrimaryKey(ArticleUser record);
}