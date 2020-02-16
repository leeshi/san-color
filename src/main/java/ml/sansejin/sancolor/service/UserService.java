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
    //TODO 因为User只用作身份验证，因此该service可以移动到security包下
    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUserById(Integer id);

    User getUserByUserName(String userName);

    List<User> listAllUsers();
}
