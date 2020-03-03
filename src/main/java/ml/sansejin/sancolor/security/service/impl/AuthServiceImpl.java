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
        User user = new User();
        user.setPassword(encoder.encode(rawPassword));
        user.setName(userToAdd.getName());
        user.setRole("ROLE_USER");  //默认具有普通用户权限

        userService.addUser(user);

        //返回修改后的user对象
        return user;
    }

    /**
     * 根据用户提交的表单生成一个token，并且将该token加入到白名单中
     * @param userName
     * @param password
     * @return token
     */
    @Override
    public String login(String userName, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
        //通过password，name验证用户
        final Authentication authentication = authenticationManager.authenticate(upToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //获得用户的信息类以进行token生成
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        //生成token
        String token = JwtTokenUtil.generateToken(userDetails);
        JwtTokenUtil.setTokenInWhiteList(token.substring(tokenHead.length()), userName);  //添加token到白名单中

        return token;
    }

    /**
     * 刷新token
     * @param oldCookieValue
     * @return null 如果不可以刷新，需要重置客户端的cookie
     *         token 如果刷新成功
     */
    @Override
    public String refresh(String oldCookieValue) {
        final String oldToken = oldCookieValue.substring(tokenHead.length());
        String userName = JwtTokenUtil.getUsernameFromToken(oldToken);  //获取token的name
        Date created = JwtTokenUtil.getCreatedDateFromToken(oldToken);  //获取token的创建日期
        //如果不能从token获取date与name，那么表示该token不合法
        //检查token是否在白名单中，如果不在，则不接受该token
        if ((created == null || userName == null) && !JwtTokenUtil.isTokenInWhiteList(oldToken, userName)) {
            return null;
        }

        //检查token是否已经过期
        if (JwtTokenUtil.isTokenExpired(created)) {
            //token已经过期，清空白名单中的内容
            JwtTokenUtil.setTokenInWhiteList(userName, null);
            return null;
        }

        //如果token在存活期内，那么就直接返回旧值，注意是cookie的原值
        if (JwtTokenUtil.isTokenAlive(created)) {
            return oldCookieValue;
        }

        //token既没有过期也不在存活期内，那么就在刷新期内，进行刷新
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(userName);
        String newToken = JwtTokenUtil.generateToken(user);  //获得新的token
        //加入新的token到白名单中
        JwtTokenUtil.setTokenInWhiteList(newToken.substring(tokenHead.length()), userName);

        return newToken;
    }
}
