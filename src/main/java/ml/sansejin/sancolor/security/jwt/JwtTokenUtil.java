package ml.sansejin.sancolor.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ml.sansejin.sancolor.security.model.JwtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

    /*
     * 验证token是否有效，并且在token存活期内不会刷新token，若在刷新时间内再次访问，那么就会进行一次刷新
     * 如果超过刷新时间，那么该token就过期
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

    public static boolean canTokenBeRefreshed(String token, Date lastModifiedDate){
        final Date created = getCreatedDateFromToken(token);

        return !isCreatedBeforeLastPasswordReset(created, lastModifiedDate)
                && !isTokenAlive(created)       //存活期内不可以刷新
                && !isTokenExpired(created);    //没过期
    }

    public static String refreshToken(String oldToken){
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(oldToken);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }


    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (expiration + alive) * 1000);
    }

    private static boolean isTokenExpired(Date created) {
        Date expirationDate = new Date(System.currentTimeMillis() - (expiration + alive) * 1000);
        return created.before(expirationDate);
    }

    private static boolean isTokenAlive(Date created) {
        return !created.before(new Date(System.currentTimeMillis() - alive * 1000));
    }

    private static boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        if (lastPasswordReset == null) {
            return false;
        } else {
            return created.before(lastPasswordReset);
        }
    }

    private static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private static Date getCreatedDateFromToken(String token) {
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
}
