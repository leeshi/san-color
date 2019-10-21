package ml.sansejin.sancolor.security.service;


import ml.sansejin.sancolor.entity.User;

/**
 * @author sansejin
 * @className AuthService
 * @description TODO
 * @create 10/20/19 5:24 PM
 **/
public interface AuthService {
    User register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
