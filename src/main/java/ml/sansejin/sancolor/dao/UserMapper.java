package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.User;
import ml.sansejin.sancolor.entity.UserExample;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}