package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dto.ArticleDTO;
import ml.sansejin.sancolor.entity.Article;
import ml.sansejin.sancolor.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author sansejin
 * @className ArticleServiceImpl
 * @description TODO
 * @create 9/18/19 5:05 PM
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public boolean addArticle(@NotNull ArticleDTO articleDTO) {

        Article article = new Article();

        article.setCreate_by(articleDTO.getCreateBy());
        article.setId(articleDTO.getId());
        //默认文章有效
        article.setSummary(articleDTO.getSummary());
        article.setTitle(articleDTO.getTitle());
        article.setVisibillity(articleDTO.getVisibillity());


        if(articleMapper.insert(article) != 0) {
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean deleteArticleById(Long id) {
        return false;
    }

    @Override
    public boolean updateArticle(ArticleDTO articleDTO) {
        return false;
    }

    @Override
    public boolean updateArticleCategory(Long articleId, Long categoryId) {
        return false;
    }

    @Override
    public boolean updateArticlePicture(Long articleId, String pictureUrl) {
        return false;
    }

    @Override
    public ArticleDTO getArticleDTOById(Long id) {
        return null;
    }

    @Override
    public List<ArticleDTO> listAllArticles() {
        return null;
    }

    @Override
    public List<ArticleDTO> listArticlesByCategoryId(Long id) {
        return null;
    }

    @Override
    public List<ArticleDTO> listLatesetArticles() {
        return null;
    }
}
