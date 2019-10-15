package ml.sansejin.sancolor.security.model;

import ml.sansejin.sancolor.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sansejin
 * @className JwtUserFactory
 * @description 该类将原生的user对象转化为可以使用的JwtUser
 * @create 10/15/19 4:01 PM
 **/
public class JwtUserFactory {
    private JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRolesList()),
                user.getModified_by()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
