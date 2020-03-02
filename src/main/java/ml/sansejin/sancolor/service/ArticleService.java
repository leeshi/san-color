package ml.sansejin.sancolor.service;

import ml.sansejin.sancolor.dto.ArticleDTO;
import ml.sansejin.sancolor.exception.NoArticleContentException;

import java.util.List;

/**
 * ArticleDTO中封装了 picture/content/user/category的相关信息
 * @author sansejin
 */

public interface ArticleService {
    boolean addArticle(ArticleDTO articleDTO, String userName);

    boolean deleteArticleById(Long id, String userName);

    boolean updateArticle(Long articleId, ArticleDTO articleDTO, String userName);

    boolean updateArticlePicture(Long articleId, String pictureUrl);

    boolean isArticleExit(Long articleId);

    ArticleDTO getArticleDTOById(Long id) throws NoArticleContentException;

    //返回指定分类的ArticleDTO
    List<ArticleDTO> listArticlesByCategoryId(Long id);

    //返回最新的ArticleDTO
    List<ArticleDTO> listLatesetArticles();
}
