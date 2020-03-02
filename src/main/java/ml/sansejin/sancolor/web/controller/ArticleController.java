package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.dto.ArticleDTO;
import ml.sansejin.sancolor.exception.NoArticleContentException;
import ml.sansejin.sancolor.security.model.JwtUser;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author sansejin
 * @className ArticleController
 * @description TODO
 * @create 9/18/19 7:49 PM
 * //TODO 添加查看某一个用户所写的文章
 **/
@RestController
@RequestMapping("/api/v1/article")
public class ArticleController extends BaseController {
    private static final Logger logger = Logger.getLogger(ArticleController.class);

    /**
     * 获取所有的文章
     * @return ResponseEntity<List<ArticleDTO>>
     */
    @GetMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<ArticleDTO>> fetchAllArticles(){
        List<ArticleDTO> listArticleDTO = articleService.listLatesetArticles();
        logger.info("Fetch all articles");
        return new ResponseEntity<>(listArticleDTO, HttpStatus.OK);
    }

    /**
     * 通过分类id获取所有文章
     * @param categoryId
     * @return ResponseEntity<List<ArticleDTO>>
     */
    @GetMapping(value = "/category/{categoryId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<List<ArticleDTO>> fetchArticlesOfCategory (@PathVariable Long categoryId) {
        List<ArticleDTO> listArticleDTO = articleService.listArticlesByCategoryId(categoryId);
        logger.info(String.format("Fetch all articles of category! Category ID:%d", categoryId));
        return new ResponseEntity<>(listArticleDTO, HttpStatus.OK);
    }

    /**
     * 获取一篇文章
     * @param articleId
     * @return ResponseEntity<ArticleDTO>
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
     * @return ResponseEntity
     * TODO 从token中获取user的id，不再从DTO中获取
     */
    @PostMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> addArticle(@RequestBody ArticleDTO articleDTO, Principal principal){
        //从已经进行了验证的用户中获取name
        articleService.addArticle(articleDTO, principal.getName());

        logger.info(String.format("Add an article! Title:%s; User:%s", articleDTO.getTitle(), principal.getName()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据id删除一篇文章
     * @param articleId
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId, Principal principal){
        if (articleService.deleteArticleById(articleId, principal.getName())) {
            logger.info(String.format("Delete an article! ID:%d", articleId));

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            //删除失败，也就是用户id不正确，返回403
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * 根据id更新一篇文章
     * @param articleId
     * @param articleDTO
     * @return ResponseEntity<Void>
     */
    @PutMapping(value = "/{articleId}",produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> updateArticle(@PathVariable Long articleId, @RequestBody ArticleDTO articleDTO,
                                              Principal principal){
        if (!articleService.isArticleExit(articleId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (articleService.updateArticle(articleId, articleDTO, principal.getName())) {
            logger.info(String.format("Update an article! ID:%d", articleId));
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            //更新失败，验证信息不匹配，返回403
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
