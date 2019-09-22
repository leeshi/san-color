package ml.sansejin.sancolor.service;

import ml.sansejin.sancolor.dto.ArticleDTO;

import java.util.List;

/**
 * ArticleDTO中封装了 picture/content/user/category的相关信息
 * @author sansejin
 */

public interface ArticleService {
    boolean addArticle(ArticleDTO articleDTO);

    boolean deleteArticleById(Long id);

    boolean updateArticle(ArticleDTO articleDTO);

    //更新文章分类
/*
    boolean updateArticleCategory(Long articleId, List<Long> categoryIds);
*/

    boolean updateArticlePicture(Long articleId, String pictureUrl);

    boolean ifArticleExit(Long articleId);

    ArticleDTO getArticleDTOById(Long id);

    //返回所有的ArticleDTO
    List<ArticleDTO> listAllArticles();

    //返回指定分类的ArticleDTO
    List<ArticleDTO> listArticlesByCategoryId(Long id);

    //返回最新的ArticleDTO
    List<ArticleDTO> listLatesetArticles();
}
