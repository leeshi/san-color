package ml.sansejin.sancolor.entity;

import java.util.Date;

public class ArticleContent {
    private Long id;

    private Date create_by;

    private Date modified_by;

    private Long article_id;

    private String raw_content;

    private String parsed_content;

    public String getRaw_content() {
        return raw_content;
    }

    public void setRaw_content(String raw_content) {
        this.raw_content = raw_content;
    }

    public String getParsed_content() {
        return parsed_content;
    }

    public void setParsed_content(String parsed_content) {
        this.parsed_content = parsed_content;
    }

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

}