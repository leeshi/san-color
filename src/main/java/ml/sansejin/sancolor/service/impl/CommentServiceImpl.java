package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dao.ArticleCommentMapper;
import ml.sansejin.sancolor.dao.CommentMapper;
import ml.sansejin.sancolor.dto.CommentDTO;
import ml.sansejin.sancolor.entity.ArticleComment;
import ml.sansejin.sancolor.entity.ArticleCommentExample;
import ml.sansejin.sancolor.entity.Comment;
import ml.sansejin.sancolor.entity.CommentExample;
import ml.sansejin.sancolor.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    @Transactional
    @Override
    public boolean addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();

        comment.setCreate_by(new Date());
        comment.setContent(commentDTO.getContent());
        comment.setEmail(commentDTO.getEmail());
        comment.setIp(commentDTO.getIp());
        comment.setName(commentDTO.getUserName());

        commentMapper.insertSelective(comment);

        //TODO 还要重新获取id，麻烦
        CommentExample commentExample = new CommentExample();
        //TODO 以这种方式获取对象，很危险
        commentExample.createCriteria().andCreate_byEqualTo(comment.getCreate_by());
        List<Comment> listComment = commentMapper.selectByExample(commentExample);

        if (listComment.size() == 0){
            return false;
        }else{
            Long commentId = listComment.get(0).getId();

            ArticleComment articleComment = new ArticleComment();

            articleComment.setCreate_by(comment.getCreate_by());
            articleComment.setArticle_id(commentDTO.getArticleId());
            articleComment.setComment_id(commentId);

            articleCommentMapper.insertSelective(articleComment);

            return true;
        }

    }

    @Transactional
    @Override
    public boolean updateComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setModified_by(new Date());
        comment.setIs_visiable(commentDTO.getVisible());

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdEqualTo(commentDTO.getCommentId());

        commentMapper.updateByExampleSelective(comment, commentExample);

        return true;
    }

    @Transactional
    @Override
    public boolean deleteCommentById(Long commentId) {
        //删除 tbl_comment_article 记录
        ArticleCommentExample articleCommentExample = new ArticleCommentExample();

        articleCommentExample.createCriteria().andComment_idEqualTo(commentId);

        articleCommentMapper.deleteByExample(articleCommentExample);
        //删除 tbl_comment_info 记录
        commentMapper.deleteByPrimaryKey(commentId);

        return true;
    }

    @Transactional
    @Override
    public int getCommentCountByArticleId(Long articleId) {
        return articleCommentMapper.selectCountByArticleId(articleId);
    }

    @Transactional
    @Override
    public List<CommentDTO> listAllCommentsByArticleId(Long articleId) {
        List<CommentDTO> listArticleCommentDTO = new ArrayList<>();

        ArticleCommentExample articleCommentExample = new ArticleCommentExample();
        articleCommentExample.createCriteria().andArticle_idEqualTo(articleId);
        List<ArticleComment> listArticleComment = articleCommentMapper.selectByExample(articleCommentExample);

        for (ArticleComment articleComment : listArticleComment) {

            Comment comment = commentMapper.selectByPrimaryKey(articleComment.getId());

            //如果不可见，就不返回该评论
            if (!comment.getIs_visiable()) {
                continue;
            }

            CommentDTO commentDTO = new CommentDTO();

            commentDTO.setArticleCommentId(articleComment.getId());
            commentDTO.setArticleId(articleComment.getArticle_id());
            commentDTO.setCommentId(articleComment.getComment_id());
            commentDTO.setContent(comment.getContent());
            commentDTO.setEmail(comment.getEmail());
            commentDTO.setUserName(comment.getName());
            commentDTO.setVisible(true);
            //记录的创建时间与评论的时间相同
            commentDTO.setCommentCreateBy(articleComment.getCreate_by());
        }

        return listArticleCommentDTO;
    }

    @Transactional
    @Override
    public List<CommentDTO> listAllComments() {
        List<CommentDTO> listCommentDTO = new ArrayList<>();
        List<ArticleComment> listArticleComment;
        CommentDTO commentDTO;
        Comment comment;

        listArticleComment = articleCommentMapper.selectAll();

        for (ArticleComment articleComment : listArticleComment) {
            commentDTO = new CommentDTO();
            comment = commentMapper.selectByPrimaryKey(articleComment.getComment_id());

            //TODO 增加异常处理
            if (comment == null){
                continue;
            }

            //两者的创建时间相同
            commentDTO.setCommentCreateBy(articleComment.getCreate_by());
            commentDTO.setEmail(comment.getEmail());
            commentDTO.setUserName(comment.getName());
            commentDTO.setContent(comment.getContent());
            commentDTO.setVisible(comment.getIs_visiable());
            commentDTO.setArticleId(articleComment.getArticle_id());
            commentDTO.setArticleCommentId(commentDTO.getArticleCommentId());
            commentDTO.setIp(comment.getIp());
            commentDTO.setCommentId(comment.getId());

            listCommentDTO.add(commentDTO);
        }

        return listCommentDTO;
    }
}
