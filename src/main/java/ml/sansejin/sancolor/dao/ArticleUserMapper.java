package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.ArticleUser;
import ml.sansejin.sancolor.entity.ArticleUserExample;

public interface ArticleUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleUser record);

    int insertSelective(ArticleUser record);

    List<ArticleUser> selectByExample(ArticleUserExample example);

    ArticleUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleUser record);

    int updateByPrimaryKey(ArticleUser record);
}