package ml.sansejin.sancolor.Utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author sansejin
 * @className PasswordEncoderTest
 * @description TODO
 * @create 3/16/20 2:09 PM
 **/
public class PasswordEncoderTest {

    @Test
    public void test() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }
}
