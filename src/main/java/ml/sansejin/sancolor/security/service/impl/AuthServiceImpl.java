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

    @Value("Head")
    private String tokenHead;

    @Autowired
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
        if (userService.getUserByUserName(userName) != null){
            return null;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        //对密码进行加密
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setModified_by(new Date());
        userToAdd.setRole("USER");

        userService.addUser(userToAdd);

        return userToAdd;
    }

    @Override
    public String login(String username, String encodedPassword) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, encodedPassword);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = JwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = JwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (JwtTokenUtil.canTokenBeRefreshed(token, user.getLastModifiedDate())){
            return JwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
