package ml.sansejin.sancolor.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author sansejin
 * @className JwtUser
 * @description TODO
 * @create 10/15/19 3:54 PM
 **/
public class JwtUser implements UserDetails {
    private final Integer id;
    private final String userName;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Date lastModifiedDate;

    public JwtUser(Integer id,
                   String username,
                   String password,
                   Collection<? extends GrantedAuthority> authorities,
                   Date lastModifiedDate){
        this.id = id;
        this.userName = username;
        this.password = password;
        this.authorities = authorities;
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * 检测账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //TODO 未添加禁用用户功能
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

}
