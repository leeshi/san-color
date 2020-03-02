package ml.sansejin.sancolor.web.controller;

import ml.sansejin.sancolor.dto.CommentDTO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sansejin
 * @className CommentController
 * @description TODO
 * @create 9/30/19 5:09 PM
 **/

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController extends BaseController{
    private static final Logger logger = Logger.getLogger(CommentController.class);

    /**
     * 获取所有的comment
     * @return ResponseEntity<List<CommentDTO>>
     * TODO 修改成获取最新的评论
     */
    @GetMapping(value = "/")
    public ResponseEntity<List<CommentDTO>> fetchAllComments() {
        List<CommentDTO> listCommentDTO;

        listCommentDTO = commentService.listAllComments();
        logger.info("Fetch all comments");

        return new ResponseEntity<>(listCommentDTO, HttpStatus.OK);
    }

    /**
     * 获取对应文章的评论
     * @param articleId
     * @return ResponseEntity<Integer>
     */
    @GetMapping(value = "/count/{articleId}")
    public ResponseEntity<Integer> fetchCountOfArticle(@PathVariable Long articleId) {
        Integer count = commentService.getCommentCountByArticleId(articleId);

        logger.info(String.format("Fetch count of comments of specific article! ID:%d", articleId));
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * 获取文章的所有评论
     * @param articleId
     * @return ResponseEntity<List<CommentDTO>>
     * TODO 评论分页
     */
    @GetMapping(value = "/article/{articleId}")
    public ResponseEntity<List<CommentDTO>> fetchCommentByArticleId(@PathVariable Long articleId) {
        List<CommentDTO> listCommentDTO = commentService.listAllCommentsByArticleId(articleId);


        if (listCommentDTO.isEmpty()){
            logger.info(String.format("No comment of specific article! ID:%d", articleId));

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            logger.info(String.format("Fetch comments of specific article! ID:%d", articleId));

            return new ResponseEntity<>(listCommentDTO, HttpStatus.OK);
        }
    }

    /**
     * 为文章添加评论
     * @param commentDTO
     * @return ResponseEntity<Void>
     * TODO 通过token解析用户，而不是通过DTO中的用户id
     */
    @PostMapping(value = "/")
    public ResponseEntity<Void> addComment(@RequestBody CommentDTO commentDTO) {
        try{
            commentDTO.setIp(request.getRemoteAddr());
            commentService.addComment(commentDTO);

            logger.info(String.format("Add a comment! Comment:%s, ArticleId:%d", commentDTO.getContent(), commentDTO.getArticleId()));

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            //如果不存在对应的comment或者article，就会返回异常码
            logger.error(String.format("Attempting to add a comment to article not exits! ArticleID:%d", commentDTO.getArticleId()));
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * 修改评论
     * @param commentId
     * @param commentDTO
     * @return ResponseEntity<Void>
     * TODO 只允许评论者丢评论进行修改
     */
    @PutMapping(value = "/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        try {
            commentDTO.setCommentId(commentId);

            commentService.updateComment(commentDTO);

            logger.info(String.format("Update a comment! ID:%d", commentId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            logger.error(String.format("Trying to add a comment to article not exits! ArticleID:%d", commentDTO.getArticleId()));

            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * 根据id删除评论
     * @param commentId
     * @return ResponseEntity<Void>
     * TODO 只有评论者才可以删除自己的评论
     */
    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        boolean rs = commentService.deleteCommentById(commentId);

        if (rs) {
            logger.info(String.format("Delete a comment! ID:%d", commentId));

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.error(String.format("Trying to add a comment to comment not exits! CommentID:%d", commentId));

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
