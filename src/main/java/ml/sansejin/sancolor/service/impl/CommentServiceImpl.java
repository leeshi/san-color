package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dao.ArticleCommentMapper;
import ml.sansejin.sancolor.dao.CommentMapper;
import ml.sansejin.sancolor.dto.ArticleCommentDTO;
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
    public boolean addComment(ArticleCommentDTO articleCommentDTO) {
        Comment comment = new Comment();

        comment.setCreate_by(new Date());
        comment.setContent(articleCommentDTO.getContent());
        comment.setEmail(articleCommentDTO.getEmail());
        comment.setIp(articleCommentDTO.getIp());
        comment.setName(articleCommentDTO.getUserName());

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
            articleComment.setArticle_id(articleCommentDTO.getArticleId());
            articleComment.setComment_id(commentId);

            articleCommentMapper.insertSelective(articleComment);

            return true;
        }

    }

    @Transactional
    @Override
    public boolean updateComment(ArticleCommentDTO articleCommentDTO) {
        Comment comment = new Comment();
        comment.setContent(articleCommentDTO.getContent());
        comment.setModified_by(new Date());
        comment.setIs_visiable(articleCommentDTO.getVisible());

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdEqualTo(articleCommentDTO.getCommentId());

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
    public List<ArticleCommentDTO> listAllCommentsByArticleId(Long articleId) {
        List<ArticleCommentDTO> listArticleCommentDTO = new ArrayList<>();

        ArticleCommentExample articleCommentExample = new ArticleCommentExample();
        articleCommentExample.createCriteria().andArticle_idEqualTo(articleId);
        List<ArticleComment> listArticleComment = articleCommentMapper.selectByExample(articleCommentExample);

        for (ArticleComment articleComment : listArticleComment) {

            Comment comment = commentMapper.selectByPrimaryKey(articleComment.getId());

            //如果不可见，就不返回该评论
            if (!comment.getIs_visiable()) {
                continue;
            }

            ArticleCommentDTO articleCommentDTO = new ArticleCommentDTO();

            articleCommentDTO.setArticleCommentId(articleComment.getId());
            articleCommentDTO.setArticleId(articleComment.getArticle_id());
            articleCommentDTO.setCommentId(articleComment.getComment_id());
            articleCommentDTO.setContent(comment.getContent());
            articleCommentDTO.setEmail(comment.getEmail());
            articleCommentDTO.setUserName(comment.getName());
            articleCommentDTO.setVisible(true);
            //记录的创建时间与评论的时间相同
            articleCommentDTO.setCommentCreateBy(articleComment.getCreate_by());
        }

        return listArticleCommentDTO;
    }

    @Transactional
    @Override
    public List<ArticleCommentDTO> listAllComments() {
        List<ArticleCommentDTO> listArticleCommentDTO = new ArrayList<>();
        List<ArticleComment> listArticleComment;
        ArticleCommentDTO articleCommentDTO;
        Comment comment;

        listArticleComment = articleCommentMapper.selectAll();

        for (ArticleComment articleComment : listArticleComment) {
            articleCommentDTO = new ArticleCommentDTO();
            comment = commentMapper.selectByPrimaryKey(articleComment.getComment_id());

            //TODO 增加异常处理
            if (comment == null){
                continue;
            }

            //两者的创建时间相同
            articleCommentDTO.setCommentCreateBy(articleComment.getCreate_by());
            articleCommentDTO.setEmail(comment.getEmail());
            articleCommentDTO.setUserName(comment.getName());
            articleCommentDTO.setContent(comment.getContent());
            articleCommentDTO.setVisible(comment.getIs_visiable());
            articleCommentDTO.setArticleId(articleComment.getArticle_id());
            articleCommentDTO.setArticleCommentId(articleCommentDTO.getArticleCommentId());
            articleCommentDTO.setIp(comment.getIp());
            articleCommentDTO.setCommentId(comment.getId());

            listArticleCommentDTO.add(articleCommentDTO);
        }

        return listArticleCommentDTO;
    }
}
