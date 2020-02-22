package ml.sansejin.sancolor.security.service.impl;

import ml.sansejin.sancolor.entity.User;
import ml.sansejin.sancolor.security.jwt.JwtTokenUtil;
import ml.sansejin.sancolor.security.model.JwtUser;
import ml.sansejin.sancolor.security.service.AuthService;
import ml.sansejin.sancolor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author sansejin
 * @className AuthServiceImpl
 * @description TODO
 * @create 10/20/19 5:26 PM
 **/

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private UserService userService;

    @Value("${token.head}")
    private String tokenHead;

    @Resource
    PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManagerBean,
            UserDetailsService userDetailsService,
            UserService userService) {
        this.authenticationManager = authenticationManagerBean;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }


    @Override
    public User register(User userToAdd) {
        final String userName = userToAdd.getName();
        //防止重复注册
        //TODO 其实这段验证是毫无必要的，完全可以在前端就完成相应的检测
        if (userService.getUserByUserName(userName) != null){
            return null;
        }

        final String rawPassword = userToAdd.getPassword();

        //TODO 更加细致的异常分类
        if (rawPassword == null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //对密码进行加密
        //为了 舍弃一些无用的信息，新建一个对象
        userToAdd = new User();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setRole("ROLE_USER");  //默认具有普通用户权限

        userService.addUser(userToAdd);

        return userToAdd;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return JwtTokenUtil.generateToken(userDetails);
    }

    @Override
    /*
     * 为已经旧的cookie指定一个新的cookie。可以在重新访问网站后调用该接口，延长cookie的有效期限
     */
    public String refresh(String oldCookieValue) {
        final String oldToken = oldCookieValue.substring(tokenHead.length());
        String username = JwtTokenUtil.getUsernameFromToken(oldToken);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (JwtTokenUtil.canTokenBeRefreshed(oldToken, user.getLastModifiedDate())){
            return JwtTokenUtil.refreshToken(oldToken);
        }
        return null;
    }
}
