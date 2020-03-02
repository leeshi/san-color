package ml.sansejin.sancolor.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ml.sansejin.sancolor.security.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sansejin
 * @className JwtTokenUtil
 * @description JWT验证工具类
 * @create 10/15/19 4:51 PM
 **/

@Component
public class JwtTokenUtil implements Serializable {
    //保存一个JwtTokenUtil实例，静态方法可以通过该对象访问redisTemplate
    private static JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    //PostConstruct只会被运行一次，将RedisTemplate实例注入到JwtTokenUtil实例中
    @PostConstruct
    public void init() {
        jwtTokenUtil = this;
        jwtTokenUtil.redisTemplate = this.redisTemplate;
    }

    private static final long serialVersionUID = 1L;

    //加密要钥匙
    private static String secret;
    @Value("${token.secret}")
    public void setSecret(String val) {
        secret = val;
    }

    //token存活期
    private static Long alive;
    @Value("${token.alive}")
    public void setAlive(long val) {
        alive = val;
    }

    //token过期时间
    private static Long expiration;
    @Value("${token.expiration}")
    public void setExpiration(long val) {
        expiration = val;
    }

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    private static String tokenHead;
    @Value("${token.head}")
    public void setTokenHead(String val) {
        tokenHead = val;
    }

    private JwtTokenUtil() {
    }

    /**
     * 从token中获取username
     * @param token
     * @return String
     */
    public static String getUsernameFromToken(String token){
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取createdDate
     * @param token
     * @return Date
     */
    public static Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 检查Token是否在白名单中。使用userName作为key。如果白名单中没有响应的记录，那么就返回true
     * @param token
     * @param userName
     * @return boolean
     * TODO 使用userId作为key
     */
    public static boolean isTokenInWhiteList(String token, String userName) {
        String savedToken = (String)jwtTokenUtil.redisTemplate.opsForValue().get(userName);
        return savedToken != null && savedToken.equals(token);
    }

    /**
     * 设置userName对应的token，在任意时刻，每个用户只接受一个token，其他的token就算合法也不会被接受
     * @param token
     * @param userName
     */
    public static void setTokenInWhiteList(String token, String userName) {
        jwtTokenUtil.redisTemplate.opsForValue().set(userName, token);
    }

    /**
     * 验证token是否有效，并且在token存活期内不会刷新token，若在刷新时间内再次访问，那么就会进行一次刷，如果超过刷新时间，那么该token就过期
     * @param token
     * @param userDetails
     * @return boolean
     */
    public static boolean validateToken(String token, UserDetails userDetails){
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);

        return (username.equals(user.getUsername())
                        && !isTokenExpired(created)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastModifiedDate()));
    }

    public static String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        return tokenHead + Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (expiration + alive) * 1000);
    }

    public static boolean isTokenExpired(Date created) {
        Date expirationDate = new Date(System.currentTimeMillis() - (expiration + alive) * 1000);
        return created.before(expirationDate);
    }

    public static boolean isTokenAlive(Date created) {
        return !created.before(new Date(System.currentTimeMillis() - alive * 1000));
    }

    private static boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        if (lastPasswordReset == null) {
            return false;
        } else {
            return created.before(lastPasswordReset);
        }
    }

}
