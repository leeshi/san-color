package ml.sansejin.sancolor.service;

import ml.sansejin.sancolor.entity.User;

import java.util.List;

/**
 * @author sansejin
 * @className UserService
 * @description 由于User信息简单，没有必要添加一个DTO
 * @create 9/18/19 3:55 PM
 **/
public interface UserService {
    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUserById(Long id);

    List<User> listAllUsers();
}
