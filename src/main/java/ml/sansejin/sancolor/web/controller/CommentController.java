package ml.sansejin.sancolor.web.controller;

import io.swagger.models.auth.In;
import ml.sansejin.sancolor.dto.ArticleCommentDTO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sansejin
 * @className CommentController
 * @description TODO
 * @create 9/30/19 5:09 PM
 **/

@RestController
@RequestMapping(value = "/api/comment")
public class CommentController extends BaseController{
    private static final Logger logger = Logger.getLogger(CommentController.class);

    @GetMapping(value = "/")
    public ResponseEntity<List<ArticleCommentDTO>> fetchAllComments() {
        List<ArticleCommentDTO> listArticleCommentDTO = new ArrayList<>();

        listArticleCommentDTO = commentService.listAllComments();

        return new ResponseEntity<>(listArticleCommentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/count/{articleId}")
    public ResponseEntity<Integer> fetchCountOfArticle(@PathVariable Long articleId) {
        Integer count = commentService.getCommentCountByArticleId(articleId);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "/article/{articleId}")
    public ResponseEntity<List<ArticleCommentDTO>> fetchCommentByArticleId(@PathVariable Long articleId) {
        List<ArticleCommentDTO> listArticleCommentDTO = commentService.listAllCommentsByArticleId(articleId);

        if (listArticleCommentDTO.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else {
            return new ResponseEntity<>(listArticleCommentDTO, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/")
    public ResponseEntity<Void> addComment(@RequestBody ArticleCommentDTO articleCommentDTO) {
        commentService.addComment(articleCommentDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody ArticleCommentDTO articleCommentDTO) {
        articleCommentDTO.setCommentId(commentId);

        commentService.updateComment(articleCommentDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
