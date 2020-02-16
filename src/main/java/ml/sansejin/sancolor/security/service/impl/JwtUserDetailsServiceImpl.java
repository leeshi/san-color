package ml.sansejin.sancolor.security.service.impl;

import ml.sansejin.sancolor.entity.User;
import ml.sansejin.sancolor.security.model.JwtUserFactory;
import ml.sansejin.sancolor.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sansejin
 * @className JwtUserDetailsServiceImpl
 * @description TODO
 * @create 10/15/19 4:09 PM
 **/

@Service("userDetailsService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    UserService userService;

    //TODO 直接使用dao进行数据库操作
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
