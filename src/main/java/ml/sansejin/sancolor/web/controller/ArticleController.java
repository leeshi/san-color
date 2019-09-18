package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.dto.ArticleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author sansejin
 * @className ArticleController
 * @description TODO
 * @create 9/18/19 7:49 PM
 **/
@RestController
@RequestMapping("/api/article")
public class ArticleController extends BaseController {

    /**
     * 获取一篇文章
     * @param id
     * @return
     */
    @GetMapping(value = "/{articleId}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<ArticleDTO> fetchArticle(@PathVariable Long articleId){
        ArticleDTO articleDTO = articleService.getArticleDTOById(articleId);

        //注意是否是空对象
        if(articleDTO != null) {
            return new ResponseEntity<>(articleDTO, HttpStatus.OK);
        }else{
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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId){
        articleService.deleteArticleById(articleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "",produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<Void> updateArticle(@RequestBody ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
