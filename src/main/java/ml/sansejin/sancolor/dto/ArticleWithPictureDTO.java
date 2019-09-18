package ml.sansejin.sancolor.dto;

import java.util.Date;

/**
 * 说明:该类关联了tbl_article_info 与 tbl_article_picture两张表，主要用于显示文章的简略信息
 * 对应的Api为 /api/picture/article/{id}
 * @author sansejin
 */

public class ArticleWithPictureDTO {
    //tbl_article_info基础字段
    private Long articleId;
    private String summary;
    private String title;
    private Date createBy;
    private Date modifiedBy;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }

    public Date getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Date modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getArticlePictureId() {
        return articlePictureId;
    }

    public void setArticlePictureId(Long articlePictureId) {
        this.articlePictureId = articlePictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    //tbl_article_picture基础字段
    private Long articlePictureId;
    private String pictureUrl;
}
