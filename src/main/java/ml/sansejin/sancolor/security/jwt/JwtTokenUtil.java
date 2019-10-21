package ml.sansejin.sancolor.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author sansejin
 * @className JwtTokenUtil
 * @description TODO
 * @create 10/15/19 4:51 PM
 **/

@Component
public class JwtTokenUtil {
    //TODO
    public static String getUsernameFromToken(String token){
        return null;
    }

    //TODO
    public static boolean validateToken(String token, UserDetails userDetails){
        return false;
    }

    //TODO
    public static String generateToken(UserDetails userDetails){
        return null;
    }

    //TODO
    public static boolean canTokenBeRefreshed(String token, Date lastModifiedDate){
        return false;
    }

    //TODO
    public static String refreshToken(String oldToken){
        return null;
    }
}
