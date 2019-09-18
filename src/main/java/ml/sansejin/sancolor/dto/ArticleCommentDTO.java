package ml.sansejin.sancolor.dto;

/**
 * 说明：通过某一篇文章获取全部的评论详细信息  该类对应的Api是 /api/comment/article/{id}
 * @author sansejin
 */

public class ArticleCommentDTO {
    //tbl_comment_info基础字段
    private Long commentId;  //评论的id
    private String content;  //评论的内容
    private String userName; //用户名
    private String email;
    private String ip;

    //tbl_comment_article基础字段
    private Long articleCommentId;
    private Long articleId;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getArticleCommentId() {
        return articleCommentId;
    }

    public void setArticleCommentId(Long articleCommentId) {
        this.articleCommentId = articleCommentId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
