package ml.sansejin.sancolor.dto;

import java.util.Date;

/**
 * 说明：文章详细信息类  该类对应的Api为/api/article/{id}
 * @author sansejin
 */
public class ArticleDTO {
    //tbl_article_info基础字段
    private Long id;
    private String summary;
    private String title;
    private Integer visibillity;
    private Integer traffic;
    private Boolean effective;
    private Date createBy;
    private Date modifiedBy;

    //tbl_article_user基础字段
    private Long userId;
    private String userName;

    //tbl_article_content基础字段
    private Long articleContentId;
    private String content;

    //tbl_article_picture基础字段
    private Long articlePictureId;
    private String pictureUrl;

    //tbl_category_article基础字段
    private Long categoryId;
    private String categoryName;



    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getVisibillity() {
        return visibillity;
    }

    public void setVisibillity(Integer visibillity) {
        this.visibillity = visibillity;
    }

    public Boolean getEffective() {
        return effective;
    }

    public void setEffective(Boolean effective) {
        this.effective = effective;
    }

    public Date getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Date createBy) {
        this.createBy = createBy;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getArticleContentId() {
        return articleContentId;
    }

    public void setArticleContentId(Long articleContentId) {
        this.articleContentId = articleContentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
