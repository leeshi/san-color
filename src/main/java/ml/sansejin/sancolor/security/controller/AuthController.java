package ml.sansejin.sancolor.security.controller;

import ml.sansejin.sancolor.entity.User;
import ml.sansejin.sancolor.security.service.AuthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sansejin
 * @className AuthController
 * @description TODO
 * @create 10/20/19 5:54 PM
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class);

    @Value("${token.name}")
    private String tokenName;

    @Resource
    AuthService authService;

    //登录接口
    @PostMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user, HttpServletResponse response){
        //登录后返回Token
        logger.info(String.format("Authenticating user: \"%s\", password received: \"%s\"", user.getName(), user.getPassword()));
        final String token = authService.login(user.getName(), user.getPassword());

        if (token == null) {
            logger.info(String.format("Fail to generate token for user: \"%s\"", user.getName()));
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        } else {
            logger.info(String.format("Success to generate token for user: \"%s\"  token: \"%s\"", user.getName(), token));
            Cookie cookie = new Cookie(tokenName, token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            //TODO 设置正确的age
            cookie.setMaxAge(60 * 60 * 24 * 3);

            response.addCookie(cookie);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

    }

    //刷新Token接口
    //TODO 刷新 token 后使旧的token 过期
    @GetMapping(value = "/")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response){
        //检测是否存在对应的cookie
        String token  = null;
        if (request.getCookies() != null) {
           for (Cookie c : request.getCookies()) {
               if (c.getName().equals(tokenName)) {
                   token = c.getValue();
                   break;
               }
           }
        }

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("You are not signed in yet.");
        } else {
            String refreshedToken = authService.refresh(token);

            if (refreshedToken == null){
                return ResponseEntity.badRequest().body("Fail to refresh. Your token may be expired!");
            } else {
                //刷新成功，返回新的token
                Cookie cookie = new Cookie(tokenName, refreshedToken);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 3);

                response.addCookie(cookie);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }

    }

    //注册接口
    @PostMapping(value = "/user")
    public ResponseEntity<?> register(@RequestBody User user){
        User userAdded = authService.register(user);

        if (userAdded == null) {
            logger.info("Attempting to register a user already exits! User Name : " + user.getName());
            return ResponseEntity.badRequest().body("User conflict");
        } else {
            logger.info("User added. User Name : " + user.getName());
            return new ResponseEntity<>("Please log in with your username and password!", HttpStatus.CREATED);
        }
    }
}
