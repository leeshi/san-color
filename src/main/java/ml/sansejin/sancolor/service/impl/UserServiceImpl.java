package ml.sansejin.sancolor.service.impl;

import ml.sansejin.sancolor.dao.UserMapper;
import ml.sansejin.sancolor.entity.User;
import ml.sansejin.sancolor.entity.UserExample;
import ml.sansejin.sancolor.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sansejin
 * @className UserServiceImpl
 * @description TODO 增加权限管理
 * @create 10/15/19 3:14 PM
 **/
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public boolean addUser(User user) {
        userMapper.insertSelective(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);

        return true;
    }

    @Override
    public boolean deleteUserById(Integer id) {
        //查看是否存在相应的用户
        if (userMapper.selectByPrimaryKey(id) == null) {
            return false;
        }

        userMapper.deleteByPrimaryKey(id);
        return true;
    }

    /**
     * 通过用户名获取用户实体，可能返回null
     * @param userName
     * @return
     */
    @Override
    public User getUserByUserName(String userName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(userName);

        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    public List<User> listAllUsers() {
        return null;
    }
}