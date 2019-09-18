package ml.sansejin.sancolor.service;

import ml.sansejin.sancolor.dto.ArticleCommentDTO;

import java.util.List;

/**
 * @author sansejin
 * @className CommentService
 * @description 因为所有的comment都会依附于文章当中，所以此处直接使用ArticleCommentDTO
 * @create 9/18/19 3:48 PM
 **/
public interface CommentService {
    boolean addComment(ArticleCommentDTO articleCommentDTO);

    boolean updateComment(ArticleCommentDTO articleCommentDTO);

    boolean deleteCommentById(Long id);

    int getCommentCountByArticleId(Long id);

    //通过某一篇文章的id来指定显示所有的comment
    List<ArticleCommentDTO> listAllCommentsByArticleId(Long id);
}
