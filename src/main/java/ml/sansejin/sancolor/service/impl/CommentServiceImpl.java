package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dto.ArticleCommentDTO;
import ml.sansejin.sancolor.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sansejin
 * @className CommentServiceImpl
 * @description TODO
 * @create 9/18/19 8:08 PM
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public boolean addComment(ArticleCommentDTO articleCommentDTO) {
        return false;
    }

    @Override
    public boolean updateComment(ArticleCommentDTO articleCommentDTO) {
        return false;
    }

    @Override
    public boolean deleteCommentById(Long id) {
        return false;
    }

    @Override
    public int getCommentCountByArticleId(Long id) {
        return 0;
    }

    @Override
    public List<ArticleCommentDTO> listAllCommentsByArticleId(Long id) {
        return null;
    }
}
