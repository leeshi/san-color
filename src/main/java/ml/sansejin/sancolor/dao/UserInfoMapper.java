package ml.sansejin.sancolor.dao;

import java.util.List;
import ml.sansejin.sancolor.entity.UserInfo;
import ml.sansejin.sancolor.entity.UserInfoExample;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}