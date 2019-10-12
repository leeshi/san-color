package ml.sansejin.sancolor.service;

import ml.sansejin.sancolor.dto.CommentDTO;

import java.util.List;

/**
 * @author sansejin
 * @className CommentService
 * @description 因为所有的comment都会依附于文章当中，所以此处直接使用ArticleCommentDTO
 * @create 9/18/19 3:48 PM
 **/
public interface CommentService {
    boolean addComment(CommentDTO commentDTO);

    boolean updateComment(CommentDTO commentDTO);

    boolean deleteCommentById(Long id);

    int getCommentCountByArticleId(Long id);

    //通过某一篇文章的id来指定显示所有的comment
    List<CommentDTO> listAllCommentsByArticleId(Long id);

    List<CommentDTO> listAllComments();
}
