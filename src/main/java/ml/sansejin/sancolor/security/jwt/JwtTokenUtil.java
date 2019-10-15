package ml.sansejin.sancolor.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author sansejin
 * @className JwtTokenUtil
 * @description TODO
 * @create 10/15/19 4:51 PM
 **/
public class JwtTokenUtil {
    public String getUsernameFromToken(String token){
        return null;
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return false;
    }
}
