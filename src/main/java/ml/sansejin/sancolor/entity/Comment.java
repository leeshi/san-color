package ml.sansejin.sancolor.entity;

import java.util.Date;

public class Comment {
    private Long id;

    private String ip;

    private Date create_by;

    private Date modified_by;

    private String content;

    private String email;

    private String name;

    private Boolean is_effective;

    private Boolean is_visiable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIs_effective() {
        return is_effective;
    }

    public void setIs_effective(Boolean is_effective) {
        this.is_effective = is_effective;
    }

    public Boolean getIs_visiable() {
        return is_visiable;
    }

    public void setIs_visiable(Boolean is_visiable) {
        this.is_visiable = is_visiable;
    }
}