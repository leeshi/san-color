package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.dto.ArticleDTO;
import ml.sansejin.sancolor.exception.NoArticleContentException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sansejin
 * @className ArticleController
 * @description TODO
 * @create 9/18/19 7:49 PM
 **/
@RestController
@RequestMapping("/api/article")
public class ArticleController extends BaseController {
    private static final Logger logger = Logger.getLogger(ArticleController.class);

    @GetMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<ArticleDTO>> fetchAllArticles(){
        List<ArticleDTO> listArticleDTO = articleService.listLatesetArticles();
        logger.info("Fetch all articles");
        return new ResponseEntity<>(listArticleDTO, HttpStatus.OK);
    }

    /**
     * 通过分类id获取所有文章
     * @param categoryId
     * @return
     */
    @GetMapping(value = "/category/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<ArticleDTO>> fetchArticlesOfCategory (@PathVariable Long categoryId) {
        List<ArticleDTO> listArticleDTO = articleService.listArticlesByCategoryId(categoryId);
        logger.info(String.format("Fetch all articles of category! Category ID:%d", categoryId));
        return new ResponseEntity<>(listArticleDTO, HttpStatus.OK);
    }

    /**
     * 获取一篇文章
     * @param id
     * @return
     */
    @GetMapping(value = "/{articleId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<ArticleDTO> fetchArticle(@PathVariable Long articleId){
        ArticleDTO articleDTO;

        try {
            articleDTO = articleService.getArticleDTOById(articleId);
        }catch (NoArticleContentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //注意是否是空对象
        if(articleDTO != null) {
            logger.info(String.format("Article found! ID: %d", articleId));
            return new ResponseEntity<>(articleDTO, HttpStatus.OK);
        }else{
            logger.info(String.format("Article not found! ID: %d", articleId));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 增加一篇文章
     * @param articleDTO
     * @return
     */
    @PostMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> addArticle(@RequestBody ArticleDTO articleDTO){
        articleService.addArticle(articleDTO);
        logger.info(String.format("Add an article! Title:%s", articleDTO.getTitle()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId){
        articleService.deleteArticleById(articleId);
        logger.info(String.format("Delete an article! ID:%d", articleId));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/",produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> updateArticle(@RequestBody ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);
        logger.info(String.format("Update an article! ID:%d", articleDTO.getId()));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
