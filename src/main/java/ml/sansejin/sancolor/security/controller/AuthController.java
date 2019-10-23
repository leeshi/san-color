package ml.sansejin.sancolor.security.controller;

import ml.sansejin.sancolor.entity.User;
import ml.sansejin.sancolor.security.jwt.JwtAuthenticationResponse;
import ml.sansejin.sancolor.security.service.AuthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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


    @Value("Head")
    private String tokenHeader;

    @Resource
    AuthService authService;

    //登录接口
    @PostMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user){
        //登录后返回Token
        logger.info(String.format("Authenticating user: \"%s\", password received: \"%s\"", user.getName(), user.getPassword()));
        final String token = authService.login(user.getName(), user.getPassword());

        if (token == null) {
            logger.info(String.format("Fail to generate token for user: \"%s\"", user.getName()));
            return ResponseEntity.badRequest().body(null);
        } else {
            logger.info(String.format("Success to generate token for user: \"%s\"  token: \"%s\"", user.getName(), token));
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        }

    }

    //刷新Token接口
    @GetMapping(value = "/")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request){
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);

        if (refreshedToken == null){
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(null);
        }
    }

    //注册接口
    @PostMapping(value = "register")
    public User register(@RequestBody User user){
        return authService.register(user);
    }
}
