package ml.sansejin.sancolor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class User {
    @JsonIgnore
    private Long id;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_by;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modified_by;

    private String name;

    private String avator_url;

    private String role;

    public String getRole() {
        return role;
    }

    @JsonIgnore
    public List<String> getRolesList() {
        List<String> roles = Arrays.asList(role.split(","));

        return roles;
    }

    public void setRole(String roles) {
        this.role = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAvator_url() {
        return avator_url;
    }

    public void setAvator_url(String avator_url) {
        this.avator_url = avator_url == null ? null : avator_url.trim();
    }
}