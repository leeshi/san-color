package ml.sansejin.sancolor.security.jwt;

import java.io.Serializable;

/**
 * @author sansejin
 * @className JwtAuthenticationResponse
 * @description TODO
 * @create 10/23/19 11:20 AM
 **/

public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String token;

    public JwtAuthenticationResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
