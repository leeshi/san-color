package ml.sansejin.sancolor.entity;

import java.util.Date;

public class ArticleCategory {
    private Long id;

    private Date create_by;

    private Date modified_by;

    private Long article_id;

    private Long cateory_id;

    private Boolean is_effective;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreate_by() {
        return create_by;
    }

    public void setCreate_by(Date create_by) {
        this.create_by = create_by;
    }

    public Date getModified_by() {
        return modified_by;
    }

    public void setModified_by(Date modified_by) {
        this.modified_by = modified_by;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public Long getCateory_id() {
        return cateory_id;
    }

    public void setCateory_id(Long cateory_id) {
        this.cateory_id = cateory_id;
    }

    public Boolean getIs_effective() {
        return is_effective;
    }

    public void setIs_effective(Boolean is_effective) {
        this.is_effective = is_effective;
    }
}