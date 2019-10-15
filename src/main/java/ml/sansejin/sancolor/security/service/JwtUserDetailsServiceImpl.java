package ml.sansejin.sancolor.security.service;

import ml.sansejin.sancolor.entity.User;
import ml.sansejin.sancolor.security.model.JwtUserFactory;
import ml.sansejin.sancolor.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;

/**
 * @author sansejin
 * @className JwtUserDetailsServiceImpl
 * @description TODO
 * @create 10/15/19 4:09 PM
 **/
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(name);

        if (user == null){
            throw new UsernameNotFoundException(String.format("No user found with name:%s.", name));
        }else {
            return JwtUserFactory.create(user);
        }
    }
}
