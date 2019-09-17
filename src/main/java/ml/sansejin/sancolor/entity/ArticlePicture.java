package ml.sansejin.sancolor.entity;

import java.util.Date;

public class ArticlePicture {
    private Long id;

    private Date create_by;

    private Date modified_by;

    private String picture_url;

    private Long atricle_id;

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

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url == null ? null : picture_url.trim();
    }

    public Long getAtricle_id() {
        return atricle_id;
    }

    public void setAtricle_id(Long atricle_id) {
        this.atricle_id = atricle_id;
    }

    public Boolean getIs_effective() {
        return is_effective;
    }

    public void setIs_effective(Boolean is_effective) {
        this.is_effective = is_effective;
    }
}